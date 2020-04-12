package CLI;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class main {
    private static boolean  quit=false;
    private static Scanner input = new Scanner(System.in);
    public static void main (String[] args){
        int choice;
        List<Callback> calls=new LinkedList<>();
        initCalls(calls);
        List<String> actions=new LinkedList<>();
        initActions(actions);

        System.out.println("Welcome to Super-Li HR System");
        while(!quit){
            System.out.println("The possible commands are:");
            for (String action: actions) {
                System.out.println(action);
            }
            System.out.println("Please choose a command:");
            try {
                choice = Integer.parseInt(input.nextLine());
            }
            catch (Exception e){
                System.out.println("illegal input");
                continue;
            }
            if(choice>actions.size()| choice<1){
                System.out.println("no such command");
                continue;
            }
            System.out.println("you have chosen the command:");
            System.out.println(actions.get(choice-1));
            calls.get(choice-1).call();
        }
    }

    private static void initCalls(List<Callback> calls){
        calls.add(new Callback() {
            @Override
            public void call() {
                int id;
                String name;
                int salary;
                int pension;
                int vacation;
                int sickDays;
                String  startDate;
                String role;
                try {
                    System.out.println("please enter the details of the new employee");
                    System.out.print("id: ");
                    id=Integer.parseInt(input.nextLine());
                    System.out.print("Full name: ");
                    name= input.nextLine();
                    System.out.print("salary: ");
                    salary=Integer.parseInt(input.nextLine());
                    System.out.print("pension: ");
                    pension=Integer.parseInt(input.nextLine());
                    System.out.print("vacation: ");
                    vacation=Integer.parseInt(input.nextLine());
                    System.out.print("sickDays: ");
                    sickDays=Integer.parseInt(input.nextLine());
                    System.out.print("startDate: ");
                    startDate=input.nextLine();
                    System.out.print("role: ");
                    role=input.nextLine();
                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

            }
        });

        calls.add(new Callback() {
            @Override
            public void call() {
                String name;
                int manager;
                String hours;
                String date;
                Map<Integer, String> workers=new HashMap<>();
                String role;
                boolean finish=false;
                try {
                    System.out.println("please enter the details of the new shift");
                    System.out.print("date: ");
                    date=input.nextLine();
                    System.out.print("hours: ");
                    hours=input.nextLine();
                    System.out.println("The available managers are:");
                    System.out.print("please enter a manager id: ");
                    manager=Integer.parseInt(input.nextLine());
                    System.out.println("Do you need more employees? (y/n)");
                    if(input.nextLine().equals("n"))
                        finish=true;
                    while(!finish){
                        System.out.println("please enter the role for the employee you require");
                        role=input.nextLine();
                        System.out.println("The available "+role+"s are:");
                        System.out.print("please enter the chosen id: ");
                        workers.put(Integer.parseInt(input.nextLine()),role);
                        System.out.println("Do you need more employees? (y/n)");
                        if(input.nextLine().equals("n"))
                            finish=true;
                    }
                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

            }
        });

        calls.add(new Callback() {
            @Override
            public void call() {
                quit=true;
                System.out.println("Exiting.");
            }
        });
    }
    private static void initActions(List<String> actions){
        actions.add("1. add new employee.");
        actions.add("2. add new shift.");
        actions.add("3. add new constraint.");
        actions.add("4. edit employee.");
        actions.add("5. edit shift.");
        actions.add("6. edit constraint.");
        actions.add("7. delete employee.");
        actions.add("8. delete shift.");
        actions.add("9. delete constraint.");
        actions.add("10. delete employee.");
        actions.add("11. delete shift.");
        actions.add("12. delete constraint.");
        actions.add("13. present weekly shift report.");
        actions.add("14. present weekly constraint report.");
        actions.add("15. Exit.");
    }
}


