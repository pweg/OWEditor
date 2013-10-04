package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;


public class AdapterWorldBuilder {
    
    private ServerUpdateAdapter sua = null;
    
    public AdapterWorldBuilder(ServerUpdateAdapter sua){
        this.sua = sua;
    }
    
    public void build(){
        initShapes();
        
    }
    
    private void initShapes() {  

        sua.createObject(0, 160, 160, 0, 0, 1, 70, 70, "Chair");
        sua.createObject(2, 260, 260, 0, 0, 1, 80, 80, "ChairofTestinghugelength");
        sua.createObject(1, 400, 400, 0, 0, 1, 200, 200, "Desk");
        sua.createObject(3, 0, 0, 0, 0, 1, 10, 10, "Tiny");
        sua.createObject(4, 100, 100, 0, 0, 1, 150, 25, "TinyVeryLong");
       
    }
    

}
