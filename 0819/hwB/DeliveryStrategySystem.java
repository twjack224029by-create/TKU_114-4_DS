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

public class MessageSenderSystem {
  public static boolean notify(MessageSender sender, String receiver, String message) {
        if (sender == null) {
            System.out.println("失敗,未指定 MessageSender 服務");
            return false;
        }
        if (receiver == null || receiver.isBlank() || message == null || message.isBlank()) {
            System.out.println("失敗,發送目標或訊息文字不得為空白");
            return false;
        }

        return sender.send(receiver, message);
    }
public static void main(String[] args) {
        System.out.println("test \n");

        MessageSender emailSender = new EmailSender();
        MessageSender smsSender = new SmsSender();
        MessageSender consoleSender = new ConsoleSender();

        System.out.println("發送測試");
        notify(emailSender, "student@example.com", "您的作業已成功提交！");
        notify(smsSender, "0912345678", "驗證碼為 8899");
        notify(consoleSender, "系統管理員", "伺服器運作正常");

        System.out.println("\n 空白與無效輸入測試");
        System.out.println("接收者為空字串:");
        notify(emailSender, "   ", "內容文字");

        System.out.println("\n 訊息內容為 null:");
        notify(smsSender, "0912345678", null);

        System.out.println("\nEmail格式不正確:");
        notify(emailSender, "invalid-email-address", "系統通知");

  
        System.out.println("\n擴充測試");
        MessageSender pushNotificationSender = new MessageSender() {
            @Override
            public boolean send(String receiver, String message) {
                if (receiver == null || receiver.isBlank() || message == null || message.isBlank()) return false;
                System.out.println("[App 推播至裝置 " + receiver + "]: " + message);
                return true;
            }
        };

        notify(pushNotificationSender, "DEVICE-UUID-9988", "您有一筆新交易通知");
    }
}
