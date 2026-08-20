abstract class EmployeeBase {
    String id;
    String name;

    public EmployeeBase(String id, String name) {
        System.out.println("執行 EmployeeBase 建構子");
        this.id = id;
        this.name = name;
    }

    public abstract double calculatePay();
}

class FullTimeEmployee extends EmployeeBase {
    double monthlySalary; 

    public FullTimeEmployee(String id, String name, double monthlySalary) {

        super(id, name);
        System.out.println("執行 FullTimeEmployee 建構子");

        if (monthlySalary < 0) {
            this.monthlySalary = 0;
        } else {
            this.monthlySalary = monthlySalary;
        }
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {
    double hourlyRate; 
    double hoursWorked; 

    public PartTimeEmployee(String id, String name, double hourlyRate, double hoursWorked) {

        super(id, name);
        System.out.println("執行 PartTimeEmployee 建構子");

        if (hourlyRate < 0) {
            this.hourlyRate = 0;
        } else {
            this.hourlyRate = hourlyRate;
        }

        if (hoursWorked < 0) {
            this.hoursWorked = 0;
        } else {
            this.hoursWorked = hoursWorked;
        }
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("建立 FullTimeEmployee 物件");
        FullTimeEmployee ft = new FullTimeEmployee("F001", "張小明", 50000);
        System.out.println("全職薪資: " + ft.calculatePay());

        System.out.println("\n 建立 PartTimeEmployee 物件測試負數邊界條件)");

        PartTimeEmployee pt = new PartTimeEmployee("P001", "李小華", -160, -10);
        System.out.println("兼職薪資 (負數已被歸零): " + pt.calculatePay());

        printOrderExplanation();
    }

    private static void printOrderExplanation() {
        System.out.println("               實際執行順序               ");
        System.out.println("當執行 new FullTimeEmployee 時：");
        System.out.println("  1. 先執行父類別建構子 (EmployeeBase)");
        System.out.println("  2. 再執行子類別建構子 (FullTimeEmployee)");
        System.out.println();
        System.out.println("當執行 new PartTimeEmployee 時：");
        System.out.println("  1. 先執行父類別建構子 (EmployeeBase)");
        System.out.println("  2. 再執行子類別建構子 (PartTimeEmployee)");
    }
}
