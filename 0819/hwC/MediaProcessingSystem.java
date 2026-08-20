interface Playable {
    void play();
}

interface Compressible {
    void compress();
}

abstract class MediaFile {
    String fileName;
    double fileSizeMB; 

    public MediaFile(String fileName, double fileSizeMB) {
        this.fileName = fileName;
        this.fileSizeMB = Math.max(0, fileSizeMB);
    }

    public String getFileName() {
        return fileName;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public abstract void printInfo();
}

class ImageFile extends MediaFile implements Compressible {
    int resolutionX; 
    int resolutionY; 

    public ImageFile(String fileName, double fileSizeMB, int resolutionX, int resolutionY) {
        super(fileName, fileSizeMB);
        this.resolutionX = Math.max(0, resolutionX);
        this.resolutionY = Math.max(0, resolutionY);
    }

    @Override
    public void printInfo() {
        System.out.println("[圖片檔案] 檔名: " + fileName + " | 大小: " + fileSizeMB + " MB | 解析度: " + resolutionX + "x" + resolutionY);
    }

    @Override
    public void compress() {
        System.out.println("進行圖片壓縮：優化 " + fileName + " 的像素陣列，減少檔案容量。");
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    int durationSeconds; 

    public AudioFile(String fileName, double fileSizeMB, int durationSeconds) {
        super(fileName, fileSizeMB);
        this.durationSeconds = Math.max(0, durationSeconds);
    }

    @Override
    public void printInfo() {
        System.out.println("[音訊檔案] 檔名: " + fileName + " | 大小: " + fileSizeMB + " MB | 長度: " + durationSeconds + " 秒");
    }

    @Override
    public void play() {
        System.out.println("開始播放音樂: " + fileName);
    }

    @Override
    public void compress() {
        System.out.println("進行音訊壓縮：調降 " + fileName + " 的取樣率 (Bitrate)。");
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    String frameRate; 

    public VideoFile(String fileName, double fileSizeMB, String frameRate) {
        super(fileName, fileSizeMB);
        this.frameRate = frameRate;
    }

    @Override
    public void printInfo() {
        System.out.println("[影片檔案] 檔名: " + fileName + " | 大小: " + fileSizeMB + " MB | 幀率: " + frameRate);
    }

    @Override
    public void play() {
        System.out.println("開始播放影片: " + fileName );
    }

    @Override
    public void compress() {
        System.out.println("影片壓縮：使用 H.264 編碼重新壓製 " + fileName + "。");
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        System.out.println("test \n");

        MediaFile[] files = new MediaFile[] {
            new ImageFile("vacation_photo.jpg", 4.5, 3840, 2160),
            new AudioFile("favorite_song.mp3", 8.2, 210),
            new VideoFile("lecture_recording.mp4", 650.0, "60fps")
        };

        for (int i = 0; i < files.length; i++) {
            MediaFile file = files[i];
            System.out.print((i + 1) + ". ");
            file.printInfo(); 

            System.out.println("   支援操作測試：");

            if (file instanceof Playable) {
                Playable p = (Playable) file;
                p.play();
            } else {
                System.out.println("   (不支援播放功能)");
            }

            if (file instanceof Compressible) {
                Compressible c = (Compressible) file;
                c.compress();
            } else {
                System.out.println("   (不支援壓縮功能)");
            }
        }
    }
}
