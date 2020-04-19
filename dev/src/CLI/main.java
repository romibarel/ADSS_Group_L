package CLI;

import Logic.Interface;

import java.text.SimpleDateFormat;
import java.util.*;

public class main {
    private static boolean  quit=false;
    private static Scanner input = new Scanner(System.in);
    public static void main (String[] args){
        int choice;
        String init;
        List<Callback> calls=new LinkedList<>();
        initCalls(calls);
        initActions(calls);

        System.out.println("Welcome to Super-Li HR System");
        System.out.println("Do you want to initialize the system with data?(y/n)");
        init=input.nextLine();
        if(init.equals("y")){
            Interface.initializeSystem();
            System.out.println("system's data loaded");
        }

        while(!quit){
            System.out.println("The possible commands are:");
            for (Callback action: calls) {
                System.out.println(action.getCommand());
            }
            System.out.println("Please choose a command:");
            try {
                choice = Integer.parseInt(input.nextLine());
            }
            catch (Exception e){
                System.out.println("illegal input");
                continue;
            }
            if(choice>calls.size()| choice<1){
                System.out.println("no such command");
                continue;
            }
            System.out.println("you have chosen the command:");
            System.out.println(calls.get(choice-1).getCommand());
            calls.get(choice-1).call();
        }
    }

    private static void initCalls(List<Callback> calls){

        //add new employee.
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentWorker w=new PresentWorker();
                try {
                    System.out.println("please enter the details of the new employee");
                    System.out.print("id: ");
                    w.setId(Integer.parseInt(input.nextLine()));
                    System.out.print("Full name: ");
                    w.setName(input.nextLine());
                    System.out.print("salary: ");
                    w.setSalary(Integer.parseInt(input.nextLine()));
                    System.out.print("pension: ");
                    w.setPension(Integer.parseInt(input.nextLine()));
                    System.out.print("vacation: ");
                    w.setVacation_days(Integer.parseInt(input.nextLine()));
                    System.out.print("sickDays: ");
                    w.setSick_days(Integer.parseInt(input.nextLine()));
                    System.out.print("startDate: ");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    w.setStart_date(format.parse(input.nextLine()));
                    System.out.print("role: ");
                    w.setRole(input.nextLine());
                    System.out.print("bank account: ");
                    w.setBank_account_number(Integer.parseInt(input.nextLine()));
                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

                System.out.println(Interface.addEmployee(w));
        }});

        //add new shift

        calls.add(new Callback() {
            @Override
            public void call() {
                PresentShift shift = new PresentShift();
                boolean finish = false;
                String role;
                try {
                    System.out.println("please enter the details of the new shift");
                    System.out.print("date: ");
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    shift.setDate(format.parse(input.nextLine()));
                    System.out.print("is it a Morning shift? (true/false)");
                    shift.setMorning(Boolean.parseBoolean(input.nextLine()));
                    System.out.println("The available managers are:");
                    String ans = Interface.getWorkersByRole("manager", shift.getDate(), shift.isMorning());
                    System.out.println(ans);
                    if (ans.equals("No workers to present for selected role, date and hour")) {
                        System.out.println("Shifts need at least one manger, cannot continue exiting back to main menu");
                        return;
                    }
                    System.out.print("please enter the chosen manger's id");
                    shift.setManager_id(Integer.parseInt(input.nextLine()));
                    System.out.println("Do you need more employees? (y/n)");
                    if (input.nextLine().equals("n"))
                        finish = true;
                    while (!finish) {
                        System.out.println("please enter the role for the employee you require");
                        role = input.nextLine();
                        System.out.println("The available " + role + "s are:");
                        ans=Interface.getWorkersByRole(role, shift.getDate(), shift.isMorning());
                        System.out.println(ans);
                        if (ans.equals("No workers to present for selected role, date and hour"))
                            continue;
                        System.out.print("please enter the chosen id: ");
                        shift.getWorkers().add(Integer.parseInt(input.nextLine()));
                        System.out.println("Do you need more employees? (y/n)");
                        if (input.nextLine().equals("n"))
                            finish = true;
                    }
                } catch (Exception e) {
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }
                System.out.println(Interface.addShift(shift));

            }});

        //add new Constraint.
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentConstraint c=new PresentConstraint();
                try {
                    System.out.println("please enter the details of the new constraint");
                    System.out.print("Employee's id: ");
                    c.setId(Integer.parseInt(input.nextLine()));
                    System.out.print("Date: ");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    c.setDate(format.parse(input.nextLine()));
                    System.out.print("is it a Morning shift you will miss? (true/false) ");
                    c.setMorning(Boolean.parseBoolean(input.nextLine()));
                    System.out.print("reason:");
                    c.setReason(input.nextLine());
                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

                System.out.println(Interface.addConstraint(c));
        }});

        //edit employee.
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentWorker w;
                int id;
                int opt=0;
                boolean finished=false;
                try {
                    System.out.println("please enter the id of the employee you want to edit");
                    id=Integer.parseInt(input.nextLine());
                    w=Interface.searchEmployee(id);
                    if(w==null){
                        System.out.println("Employee not found, Exiting to main menu");
                        return;
                    }
                    while(!finished){
                        System.out.println("The employee you selected is\n"+w.toString());
                        System.out.println("Please choose a field you want to change:");
                        System.out.println("1. Full name");
                        System.out.println("2. salary");
                        System.out.println("3. pension");
                        System.out.println("4. vacation days");
                        System.out.println("5. sick days");
                        System.out.println("6. start date");
                        System.out.println("7. role");
                        System.out.println("8. bank account");
                        System.out.println("9. finished editing");
                        opt=Integer.parseInt(input.nextLine());
                        switch (opt){
                            case 1:
                                System.out.print("Enter new Full name:");
                                w.setName(input.nextLine());
                                break;
                            case 2:
                                System.out.print("Enter new salary:");
                                w.setSalary(Integer.parseInt(input.nextLine()));
                                break;
                            case 3:
                                System.out.print("Enter new pension:");
                                w.setPension(Integer.parseInt(input.nextLine()));
                                break;
                            case 4:
                                System.out.print("Enter new vacation days:");
                                w.setVacation_days(Integer.parseInt(input.nextLine()));
                                break;
                            case 5:
                                System.out.print("Enter new sick days:");
                                w.setSick_days(Integer.parseInt(input.nextLine()));
                                break;
                            case 6:
                                System.out.print("Enter new start date:");
                                SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                                format.setLenient(false);
                                w.setStart_date(format.parse(input.nextLine()));
                                break;
                            case 7:
                                System.out.print("Enter new role:");
                                w.setRole(input.nextLine());
                                break;
                            case 8:
                                System.out.print("Enter new bank account:");
                                w.setBank_account_number(Integer.parseInt(input.nextLine()));
                                break;
                            case 9:
                                System.out.print("updating...");
                                finished=true;
                                break;
                            default:
                                System.out.println("wrong command, please try again");
                        }
                    }

                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

                System.out.println(Interface.editEmployee(w));
        }});

        //edit shift.
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentShift s;
                Date oldDate;
                boolean isMorning;
                int opt=0;
                boolean finished=false;
                try {
                    System.out.println("please enter the Date of the shift you want to edit");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    s=Interface.searchShift(oldDate,isMorning);
                    if(s==null){
                        System.out.println("Shift not found, Exiting to main menu");
                        return;
                    }
                    while(!finished){
                        System.out.println("The shift you selected is\n"+s.toString());
                        System.out.println("Please choose a field you want to change:");
                        System.out.println("1. date");
                        System.out.println("2. morning");
                        System.out.println("3. manager_id");
                        System.out.println("4. add worker");
                        System.out.println("5. remove worker");
                        System.out.println("6. finished editing");
                        opt=Integer.parseInt(input.nextLine());
                        switch (opt){
                            case 1:
                                System.out.print("Enter new date:");
                                System.out.print("Enter new start date:");
                                format.setLenient(false);
                                s.setDate(format.parse(input.nextLine()));
                                break;
                            case 2:
                                System.out.print("is it a morning shift? (true/false)");
                                s.setMorning(Boolean.parseBoolean(input.nextLine()));
                                break;
                            case 3:
                                String ans=Interface.getWorkersByRole("manager", s.getDate(),s.isMorning());
                                System.out.println(ans);
                                if(ans.equals("No workers to present for selected role, date and hour"))
                                {
                                    System.out.println("Shifts need at least one manger, cannot continue exiting back to main menu");
                                    return;
                                }
                                System.out.print("Enter new manager id:");
                                s.setManager_id(Integer.parseInt(input.nextLine()));
                                break;
                            case 4:
                                System.out.println("The current employees are:");
                                for(Integer e: s.getWorkers())
                                    System.out.println(Interface.searchEmployee(e).toString()+"\n");
                                System.out.print("please enter the id of the employee you wish to add:");
                                int id= Integer.parseInt(input.nextLine());
                                if(s.getWorkers().contains(id))
                                    System.out.println("employee is already in this shift");
                                else
                                    s.getWorkers().add(id);
                                break;
                            case 5:
                                System.out.println("The current employees are:");
                                for(Integer e: s.getWorkers())
                                    System.out.println(Interface.searchEmployee(e).toString()+"\n");
                                System.out.print("please enter the id of the employee you wish to remove");
                                id= Integer.parseInt(input.nextLine());
                                if(s.getWorkers().contains(id))
                                    s.getWorkers().remove(id);
                                else
                                    System.out.println("wrong id");
                                break;
                            case 6:
                                System.out.print("updating...");
                                finished=true;
                                break;
                            default:
                                System.out.println("wrong command, please try again");
                        }
                    }

                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

                System.out.println(Interface.editShift(s,oldDate,isMorning ));
            }});


        //edit constraint.
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentConstraint c;
                List<PresentConstraint> constraints;
                Date oldDate;
                boolean isMorning;
                int id;
                int opt=0;
                boolean finished=false;
                try {
                    System.out.println("please enter the employee's id who's constraint you want to edit");
                    id=Integer.parseInt(input.nextLine());
                    System.out.println("please enter the Date of the constraint you want to edit");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift constraint? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    constraints=Interface.searchConstraint(id, oldDate,isMorning);
                    if(constraints.isEmpty()){
                        System.out.println("Constraint not found, Exiting to main menu");
                        return;
                    }
                    System.out.println("The constraints the system found are:");
                    int i=1;
                    for(PresentConstraint con: constraints){
                        System.out.println(i+". "+ con.toString());
                        i++;
                    }
                    System.out.println("please choose the constraint you want to edit");
                    i=Integer.parseInt(input.nextLine());
                    if(i<1|| i>constraints.size())
                    {
                        System.out.println("Error, Exiting to main menu");
                        return;
                    }
                    c=constraints.get(i-1);

                    while(!finished){
                        System.out.println("The constraint you selected is\n"+c.toString());
                        System.out.println("Please choose a field you want to change:");
                        System.out.println("1. date");
                        System.out.println("2. morning");
                        System.out.println("3. Employee's id");
                        System.out.println("4. reason");
                        System.out.println("5. finished editing");
                        opt=Integer.parseInt(input.nextLine());
                        switch (opt){
                            case 1:
                                System.out.print("Enter new date:");
                                System.out.print("Enter new start date:");
                                format.setLenient(false);
                                c.setDate(format.parse(input.nextLine()));
                                break;
                            case 2:
                                System.out.print("is it a morning shift? (true/false)");
                                c.setMorning(Boolean.parseBoolean(input.nextLine()));
                                break;
                            case 3:
                                System.out.print("Enter new Employee's id:");
                                c.setId(Integer.parseInt(input.nextLine()));
                                break;
                            case 4:
                                System.out.print("Enter new reason:");
                                c.setReason(input.nextLine());
                                break;
                            case 5:
                                System.out.print("updating...");
                                finished=true;
                                break;
                            default:
                                System.out.println("wrong command, please try again");
                        }
                    }

                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

                System.out.println(Interface.editConstraint(c));
        }});


        // delete employee
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentWorker w;
                int id;
                String ans;
                try {
                    System.out.println("please enter the id of the employee you want to delete");
                    id = Integer.parseInt(input.nextLine());
                    w = Interface.searchEmployee(id);
                    if (w == null) {
                        System.out.println("Employee not found, Exiting to main menu");
                        return;
                    }
                    System.out.println("The employee you selected is\n"+w.toString());
                    System.out.println(" are you sure you want to delete this employee?(y/n)");
                    ans=input.nextLine();
                    switch (ans){
                        case "y":
                            System.out.println(Interface.deleteEmployee(w));
                            break;
                        case "n":
                            System.out.println("exiting back to main menu");
                            break;
                        default:
                            System.out.println("wrong command, exiting back to main menu");

                    }

                } catch (Exception e) {
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }
        }});

        // delete shift
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentShift s;
                Date oldDate;
                boolean isMorning;
                String ans;
                try {
                    System.out.println("please enter the Date of the shift you want to delete");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    s=Interface.searchShift(oldDate,isMorning);
                    if(s==null){
                        System.out.println("Shift not found, Exiting to main menu");
                        return;
                    }
                    System.out.println("The shift you selected is\n"+s.toString());
                    System.out.println("are you sure you want to delete this shift?(y/n)");
                    ans=input.nextLine();
                    switch (ans){
                        case "y":
                            System.out.println(Interface.deleteShift(s));
                            break;
                        case "n":
                            System.out.println("exiting back to main menu");
                            break;
                        default:
                            System.out.println("wrong command, exiting back to main menu");

                    }

                } catch (Exception e) {
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }
        }});


        // delete constraint
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentConstraint c;
                List<PresentConstraint> constraints;
                Date oldDate;
                boolean isMorning;
                int id;
                String ans;
                try {
                    System.out.println("please enter the employee's id who's constraint you want to delete");
                    id=Integer.parseInt(input.nextLine());
                    System.out.println("please enter the Date of the constraint you want to delete");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift constraint? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    constraints=Interface.searchConstraint(id, oldDate,isMorning);
                    if(constraints.isEmpty()){
                        System.out.println("Constraint not found, Exiting to main menu");
                        return;
                    }
                    System.out.println("The constraints the system found are:");
                    int i=1;
                    for(PresentConstraint con: constraints){
                        System.out.println(i+". "+ con.toString());
                        i++;
                    }
                    System.out.println("please choose the constraint you want to delete");
                    i=Integer.parseInt(input.nextLine());
                    if(i<1|| i>constraints.size())
                    {
                        System.out.println("Error, Exiting to main menu");
                        return;
                    }
                    c=constraints.get(i-1);
                    System.out.println("The constraint you selected is\n"+c.toString());
                    System.out.println("are you sure you want to delete this constraint?(y/n)");
                    ans=input.nextLine();
                    switch (ans){
                        case "y":
                            System.out.println(Interface.deleteConstraint(c));
                            break;
                        case "n":
                            System.out.println("exiting back to main menu");
                            break;
                        default:
                            System.out.println("wrong command, exiting back to main menu");

                    }

                } catch (Exception e) {
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }
        }});

        // search employee
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentWorker w;
                int id;
                String ans;
                try {
                    System.out.println("please enter the id of the employee you want to search");
                    id = Integer.parseInt(input.nextLine());
                    w = Interface.searchEmployee(id);
                    if (w == null) {
                        System.out.println("Employee not found, Exiting to main menu");
                        return;
                    }
                    System.out.println("The employee you searched is\n"+w.toString());

                } catch (Exception e) {
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }
        }});

        // search shift
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentShift s;
                Date oldDate;
                boolean isMorning;
                String ans;
                try {
                    System.out.println("please enter the Date of the shift you want to search");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    s=Interface.searchShift(oldDate,isMorning);
                    if(s==null){
                        System.out.println("Shift not found, Exiting to main menu");
                        return;
                    }
                    System.out.println("The shift you searched is\n"+s.toString());
                } catch (Exception e) {
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }
            }});


        // search constraint
        calls.add(new Callback() {
            @Override
            public void call() {
                PresentConstraint c;
                List<PresentConstraint> constraints;
                Date oldDate;
                boolean isMorning;
                int id;
                String ans;
                try {
                    System.out.println("please enter the employee's id who's constraint you want to search");
                    id=Integer.parseInt(input.nextLine());
                    System.out.println("please enter the Date of the constraint you want to search");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift constraint? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    constraints=Interface.searchConstraint(id, oldDate,isMorning);
                    if(constraints.isEmpty()){
                        System.out.println("Constraint not found, Exiting to main menu");
                        return;
                    }
                    System.out.println("The constraints the system found are:");
                    int i=1;
                    for(PresentConstraint con: constraints){
                        System.out.println(i+". "+ con.toString());
                        i++;
                    }
                } catch (Exception e) {
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }
        }});


        //shift report
        calls.add(new Callback() {
            @Override
            public void call() {
                System.out.println(Interface.shiftReport());
            }
        });

        //constraint report
        calls.add(new Callback() {
            @Override
            public void call() {
                System.out.println(Interface.constraintReport());
            }
        });

        //exit
        calls.add(new Callback() {
            @Override
            public void call() {
                quit=true;
                System.out.println("Exiting.");
            }
        });
    }
    private static void initActions(List<Callback> actions){
        actions.get(0).setCommand("1. add new employee.");
        actions.get(1).setCommand("2. add new shift.");
        actions.get(2).setCommand("3. add new constraint.");
        actions.get(3).setCommand("4. edit employee.");
        actions.get(4).setCommand("5. edit shift.");
        actions.get(5).setCommand("6. edit constraint.");
        actions.get(6).setCommand("7. delete employee.");
        actions.get(7).setCommand("8. delete shift.");
        actions.get(8).setCommand("9. delete constraint.");
        actions.get(9).setCommand("10. search employee.");
        actions.get(10).setCommand("11. search shift.");
        actions.get(11).setCommand("12. search constraint.");
        actions.get(12).setCommand("13. present weekly shift report.");
        actions.get(13).setCommand("14. present weekly constraint report.");
        actions.get(14).setCommand("15. Exit.");
    }
}


