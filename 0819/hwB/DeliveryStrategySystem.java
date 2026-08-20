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
            System.out.println("【Email 傳送失敗】無效的 Email 格式: " + receiver);
            return false;
        }
        System.out.println("[EMAIL 傳送至 " + receiver + "]: " + message);
        return true;
    }
}
