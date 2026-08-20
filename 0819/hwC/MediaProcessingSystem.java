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

