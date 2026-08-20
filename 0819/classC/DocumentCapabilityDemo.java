interface Exportable {
    void exportToFile(String path);
}


interface Compressible {
    void compress(String algorithm);
}

class BackupDocument implements Exportable, Compressible {
    String docName;

    
    public BackupDocument(String docName) {
        this.docName = docName;
    }


    @Override
    public void exportToFile(String path) {
        System.out.println("成功把檔案 " + docName + " 匯出到: " + path);
    }

    
    @Override
    public void compress(String algorithm) {
        System.out.println("成功使用 " + algorithm + " 演算法壓縮檔案: " + docName);
    }

    
    public void printName() {
        System.out.println("檔案名稱是: " + docName);
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        System.out.println("測試");

        BackupDocument myDoc = new BackupDocument("test.docx");
」
        Exportable exp = myDoc;      
        Compressible com = myDoc;    

        System.out.println("exp 和 com 是不是指向同一個物件？");
        System.out.println(exp == com); 

        System.out.println("\n測試呼叫功能");

        exp.exportToFile("/Users/student/Desktop");

        com.compress("ZIP");

        System.out.println("\n 原始型態 myDoc 呼叫");
        myDoc.exportToFile("/Users/student/Desktop");
        myDoc.compress("RAR");
        myDoc.printName();

    }
}
