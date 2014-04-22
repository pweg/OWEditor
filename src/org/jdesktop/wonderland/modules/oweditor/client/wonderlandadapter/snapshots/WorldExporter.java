/**
 * Open Wonderland
 *
 * Copyright (c) 2011 - 2012, Open Wonderland Foundation, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The Open Wonderland Foundation designates this particular file as
 * subject to the "Classpath" exception as provided by the Open Wonderland
 * Foundation in the License file that accompanied this code.
 */
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.snapshots;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.ModelCell;
import org.jdesktop.wonderland.client.cell.asset.AssetUtils;
import org.jdesktop.wonderland.client.cell.view.AvatarCell;
import org.jdesktop.wonderland.client.content.annotation.ContentExporter;
import org.jdesktop.wonderland.client.jme.ViewManager;
import org.jdesktop.wonderland.client.jme.utils.ScenegraphUtils;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.common.cell.messages.CellServerStateRequestMessage;
import org.jdesktop.wonderland.common.cell.messages.CellServerStateResponseMessage;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerStateFactory;
import org.jdesktop.wonderland.common.cell.state.PositionComponentServerState;
import org.jdesktop.wonderland.common.messages.ResponseMessage;
import org.jdesktop.wonderland.common.utils.ScannedClassLoader;

/**
 *
 * @author WonderlandWednesday
 
 Refactored class to include core api of ContentWorldExporter
 * 
 * @author JagWire
 * 
 * Changed a bit, to export the whole world.
 * 
 * @author Patrick
 */
@ContentExporter
public class WorldExporter{
    private static final Logger LOGGER = Logger.getLogger(WorldExporter.class.getName());
    
    public static WorldExporter getInstance() {
        return new WorldExporter();
    }
                
    
    public Class[] getCellClasses() {
        return new Class[] { Cell.class, ModelCell.class };
    }

    /**
     * Exports all cells currently available to a file.
     * 
     * @param filepath The filepath to export the cells.
     */
    public void exportAllCells(File file) {     

        try {
            File rootDir = File.createTempFile("subsnapshot", "tmp");
            rootDir.delete();
            rootDir.mkdir();
            File contentDir = new File(rootDir, "content");
            File serverStateDir = new File(rootDir, "server-states");
            
            CellTransform origin2 = ViewManager.getViewManager()
                    .getPrimaryViewCell().getWorldTransform();
                    
            CellCache cache = ClientContext.getCellCache(
                    LoginManager.getPrimary().getPrimarySession());
            Collection<Cell> rootCells = cache.getRootCells();
            for (Cell cell : rootCells) {
                //avatarcells can not be exported!
                if(!(cell instanceof AvatarCell))
                    exportCell(cell, origin2, contentDir, serverStateDir);
            }
            createPackage(rootDir, file);
            
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Error exporing cells", ex);
        }
    }
    
    public static List<CustomExporterSPI> getCustomExporters(Cell cell) {
        List<CustomExporterSPI> exporters = new ArrayList<CustomExporterSPI>();
        exporters.add(new GenericExporter());
        if(cell instanceof ModelCell) {
            exporters.add(new ModelCellExporter());
        }
        return exporters;
    }
    
    private CellServerState getServerState(Cell cell) {
        ResponseMessage rm = cell.sendCellMessageAndWait(
                //CellServerState requst message here.
                new CellServerStateRequestMessage(cell.getCellID()));
        if (rm == null) {
                return null;
        }
        CellServerStateResponseMessage stateMessage = (CellServerStateResponseMessage) rm;
        CellServerState state = stateMessage.getCellServerState();
        return state;
    }

    public void exportCell(Cell cell, CellTransform origin, 
                           File contentDir, File serverStateDir)
    {
        CellServerState state = getServerState(cell);
        if(state == null) {
            LOGGER.warning("Unable to retrieve server state for: " +cell.getName());
            return;
        }
        
        if(cell.getParent() == null) {
            if (origin != null) {
                // normalize the location
                PositionComponentServerState position = (PositionComponentServerState) state.getComponentServerState(PositionComponentServerState.class);
                if (position == null) {
                    position = new PositionComponentServerState();
                }
                CellTransform relativeTransform = getRelativeTransform(origin, cell.getWorldTransform());
                position.setTranslation(relativeTransform.getTranslation(null));
                position.setRotation(relativeTransform.getRotation(null));
                state.addComponentServerState(position);

            }
        }
        
        StringWriter sWriter = new StringWriter();
        try {
            ScannedClassLoader loader =
                    cell.getCellCache().getSession().getSessionManager().getClassloader();
            
            state.encode(sWriter, CellServerStateFactory.getMarshaller(loader));
            LOGGER.fine(sWriter.getBuffer() + "");
            String s = sWriter.getBuffer().toString();

            List <String> uriList = new ArrayList();
            for(CustomExporterSPI exporter: getCustomExporters(cell)) {
                uriList.addAll(exporter.getListOfContent(s));
            }

            
            downloadContent(contentDir, uriList);
            
            writeServerState(serverStateDir, s, cell, state);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,null,e);
        }
        File childDir = new File(serverStateDir, getStateFilename(cell, state)+"-children");

        for(Cell child: cell.getChildren()) {
            exportCell(child, origin, contentDir, childDir);
        }
    }

    protected CellTransform getRelativeTransform(CellTransform avatar,
                                                 CellTransform object)
    {
        return ScenegraphUtils.computeChildTransform(avatar, object);
    }

    protected void createPackage(File rootDir, File outputFile)
            throws FileNotFoundException, IOException {
        
        ZipOutputStream zStream = new ZipOutputStream(new FileOutputStream(outputFile));
        File[] list = rootDir.listFiles();
        for (int i = 0; i < list.length; i++) {
            File file = list[i];
            addToZip(file, zStream, "");
        }

        zStream.close();
    }

    private void addToZip(File file, ZipOutputStream zStream, String parentDir) throws IOException {
        
        if (file.isDirectory()) {
            String dir = parentDir + file.getName() + "/";
            ZipEntry zEntry = new ZipEntry(dir);
            zStream.putNextEntry(zEntry);
            zStream.closeEntry();
            File[] list = file.listFiles();
            for (int i = 0; i < list.length; i++) {
                File fileInDir = list[i];
                addToZip(fileInDir, zStream, dir); // drills down into the next level of the directory
            }

        } else {
            // just a file
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry zEntry = new ZipEntry(parentDir + file.getName());
            zStream.putNextEntry(zEntry);
            byte[] bArray = new byte[2048];
            int count = 0;
            while ((count = bis.read(bArray, 0, 2048)) > -1) {
                zStream.write(bArray, 0, count);
            }
            bis.close();
            zStream.closeEntry();
        }
    }
    
    public String getStateFilename(Cell cell, CellServerState state) {
        return state.getName() + cell.getCellID();
    }
    protected void writeServerState(File serverStateDir, String s, Cell cell, CellServerState state)
            throws IOException {

        //CellServerStatenumbers
        String stateFile = getStateFilename(cell, state);
        File serverState = new File(serverStateDir, stateFile);

        serverState.getParentFile().mkdirs();

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(serverState)));
        pw.println(s);
        pw.close();


    }

    protected void downloadContent(File contentDir, List<String> uriList) {

        for (String uri : uriList) {
            
            String newName = extractDirectory(uri);
           
            File content = new File(contentDir, newName);

            // create all necessary parent directories
            content.getParentFile().mkdirs();

            try {
                URL url = AssetUtils.getAssetURL(uri);
                doDownloadContent(url.openStream(), new FileOutputStream(content));
            } catch (IOException e) {
                LOGGER.info("having technical difficulties saving your URI...\n"
                        + e.getMessage());
            }
        }
    }

    protected String extractDirectory(String name) {
        // name of the form wlcontent://users@xx.yy.zz/Jonathan/art
        // return /Jonathan/art
        int slashIndex = name.indexOf("/", "wlcontent://".length());
        return name.substring(slashIndex);
    }

    protected void doDownloadContent(InputStream instream, OutputStream outstream)
            throws IOException {
        byte[] buffer = new byte[16 * 1024];

        int read;
        while ((read = instream.read(buffer)) > 0) {
            outstream.write(buffer, 0, read);
        }

        outstream.flush();
    }

    static class GenericExporter implements CustomExporterSPI{

        public List<String> getListOfContent(String s) {
            List<String> uriList = new ArrayList();
            int uri0 = 0;
            int uri1 = 0;

            while (true) {
                uri0 = s.indexOf("wlcontent:", uri1);
                if (uri0 == -1) {
                    break;
                }
                uri1 = s.indexOf("</", uri0);
                if (uri1 == -1) {
                    break;
                }
                String s1 = s.substring(uri0, uri1);
                uriList.add(s1);
            }

            return uriList;
        }
    }
}
