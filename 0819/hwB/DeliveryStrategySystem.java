import java.util.Objects;

interface MessageSender {    
  boolean send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() || message == null || message.isBlank()) {
            System.out.println("傳送失敗,接收者或訊息內容不得為空");
            return false;
        }
        if (!receiver.contains("@")) {
            System.out.println("失敗,無效的 Email 格式: " + receiver);
            return false;
        }
        System.out.println("[EMAIL 傳送至 " + receiver + "]: " + message);
        return true;
    }
}

class SmsSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() || message == null || message.isBlank()) {
            System.out.println("傳送失敗,接收者或訊息內容不得為空");
            return false;
        }
        System.out.println("[SMS傳送至 " + receiver + "]: " + message);
        return true;
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() || message == null || message.isBlank()) {
            System.out.println("傳送失敗,接收者或訊息內容不得為空");
            return false;
        }
        System.out.println("[CONSOLE 列印 -> " + receiver + "]: " + message);
        return true;
    }
}
