abstract class Employee {
    String id;
    String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract double calculatePay();

    @Override
    public String toString() {
        return String.format("工號: %-5s  姓名: %-4s  應發薪資: $%8.2f", id, name, calculatePay());
    }
}

class SalariedEmployee extends Employee {
    double monthlySalary; 

    public SalariedEmployee(String id, String name, double monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary); 
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    double hourlyRate;  
    double hoursWorked; 

    public HourlyEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hoursWorked = Math.max(0, hoursWorked);
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

class CommissionEmployee extends Employee {
    double baseSalary;
    double salesAmount;
    double commissionRate; 

    public CommissionEmployee(String id, String name, double baseSalary, double salesAmount, double commissionRate) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionRate = Math.max(0, commissionRate);
    }

    @Override
    public double calculatePay() {
        return baseSalary + (salesAmount * commissionRate);
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        System.out.println("薪資與獎金系統 \n");

        Employee[] employees = new Employee[] {
            new SalariedEmployee("E001", "陳經理", 65000),
            new HourlyEmployee("E002", "林工讀", 185, 120),       
            new CommissionEmployee("E003", "張業務", 30000, 500000, 0.08), 
            new SalariedEmployee("E004", "黃專員", 42000)
        };

        double totalPayroll = 0;
        System.out.println("薪資明細");
        for (Employee emp : employees) {
            System.out.println(emp.toString());
            totalPayroll += emp.calculatePay(); 
        }

        Employee topEarner = employees[0];
        for (int i = 1; i < employees.length; i++) {
            if (employees[i].calculatePay() > topEarner.calculatePay()) {
                topEarner = employees[i];
            }
        }

        System.out.printf("\n發薪統計結果 %n");
        System.out.printf("公司應付薪資總額: $%.2f 元%n", totalPayroll);
        System.out.printf("本月最高薪資員工: %s (%s)，薪資共計: $%.2f 元%n",
                topEarner.getName(), topEarner.getId(), topEarner.calculatePay());
    }
}
