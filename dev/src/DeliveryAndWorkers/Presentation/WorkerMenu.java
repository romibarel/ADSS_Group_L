package DeliveryAndWorkers.Presentation;

import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceConstraint;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceShift;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceWorker;
import DeliveryAndWorkers.Interface.Interface;
import sun.plugin2.message.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WorkerMenu {
    private static boolean  quit=false;
    private static Scanner input = new Scanner(System.in);
    private static List<Callback> calls=new LinkedList<>();

    public static void mainFunc (){
        int choice;
        int m=0;
        List<String> messages;
        String init;
        initCalls(calls);
        initActions(calls);
        System.out.println("Welcome to Super-Li HR System");

        messages=Interface.getMessages();
        if(!messages.isEmpty())
            System.out.println("you have new messages:");
        for(String mess: messages){
            System.out.println(m+") "+mess);
            m++;
        }
        while(!quit){
            System.out.println("Please choose from the following menus");
            System.out.println("1. Add menu");
            System.out.println("2. Edit menu");
            System.out.println("3. Delete menu");
            System.out.println("4. Search menu");
            System.out.println("5. Report menu");
            System.out.println("6. Exit HR system");
            System.out.println("Please choose a command:");
            try {
                choice = Integer.parseInt(input.nextLine());
            }
            catch (Exception e){
                System.out.println("illegal input");
                continue;
            }
            if(choice>6| choice<1){
                System.out.println("no such menu");
                continue;
            }
           switch (choice)
           {
               case 1:
                   System.out.println("Add menu:");
                   addMenu();
                   break;
               case 2:
                   System.out.println("Edit menu:");
                   editMenu();
                   break;
               case 3:
                   System.out.println("Delete menu:");
                   deleteMenu();
                   break;
               case 4:
                   System.out.println("Search menu:");
                   searchMenu();
                   break;
               case 5:
                   System.out.println("Report menu:");
                   reportMenu();
                   break;
               case 6:
                   exit();
                   break;
               default:
                   System.out.println("Could not find menu");
           }
        }
    }
    public static void mangerMain (){
        int choice;
        int m=0;
        List<String> messages;
        String init;
        initCalls(calls);
        initActions(calls);
        System.out.println("Welcome to Super-Li manager HR System");
        while(!quit){
            System.out.println("Please choose from the following menus");
            System.out.println("1. Search menu");
            System.out.println("2. Report menu");
            System.out.println("3. Exit HR system");
            System.out.println("Please choose a command:");
            try {
                choice = Integer.parseInt(input.nextLine());
            }
            catch (Exception e){
                System.out.println("illegal input");
                continue;
            }
            if(choice>3| choice<1){
                System.out.println("no such menu");
                continue;
            }
            switch (choice)
            {
                case 1:
                    System.out.println("Search menu:");
                    searchMenu();
                    break;
                case 2:
                    System.out.println("Report menu:");
                    reportMenu();
                    break;
                case 3:
                    exit();
                    break;
                default:
                    System.out.println("Could not find menu");
            }
        }
    }

    private static void exit(){
        quit=true;
        System.out.println("Exiting.");
    }

    private static void addMenu(){
        int choice=0;
        System.out.println("The possible add commands are:");
        for (int i=0; i<3; i++) {
            System.out.println(calls.get(i).getCommand());
        }
        System.out.println("Please choose a command:");
        try {
            choice = Integer.parseInt(input.nextLine());
        }
        catch (Exception e){
            System.out.println("illegal input");
            return;
        }
        if(choice>3| choice<1){
            System.out.println("no such command");
            return;
        }
        System.out.println("you have chosen the command:");
        System.out.println(calls.get(choice-1).getCommand()+"\n");
        calls.get(choice-1).call();
    }

    private static void editMenu(){
        int choice=0;
        System.out.println("The possible edit commands are:");
        for (int i=3; i<6; i++) {
            System.out.println(calls.get(i).getCommand());
        }
        System.out.println("Please choose a command:");
        try {
            choice = Integer.parseInt(input.nextLine());
        }
        catch (Exception e){
            System.out.println("illegal input");
            return;
        }
        if(choice>3| choice<1){
            System.out.println("no such command");
            return;
        }
        System.out.println("you have chosen the command:");
        System.out.println(calls.get(choice+2).getCommand()+"\n");
        calls.get(choice+2).call();
    }

    private static void deleteMenu(){
        int choice=0;
        System.out.println("The possible delete commands are:");
        for (int i=6; i<9; i++) {
            System.out.println(calls.get(i).getCommand());
        }
        System.out.println("Please choose a command:");
        try {
            choice = Integer.parseInt(input.nextLine());
        }
        catch (Exception e){
            System.out.println("illegal input");
            return;
        }
        if(choice>3| choice<1){
            System.out.println("no such command");
            return;
        }
        System.out.println("you have chosen the command:");
        System.out.println(calls.get(choice+5).getCommand()+"\n");
        calls.get(choice+5).call();
    }

    private static void searchMenu(){
        int choice=0;
        System.out.println("The possible delete commands are:");
        for (int i=9; i<12; i++) {
            System.out.println(calls.get(i).getCommand());
        }
        System.out.println("Please choose a command:");
        try {
            choice = Integer.parseInt(input.nextLine());
        }
        catch (Exception e){
            System.out.println("illegal input");
            return;
        }
        if(choice>3| choice<1){
            System.out.println("no such command");
            return;
        }
        System.out.println("you have chosen the command:");
        System.out.println(calls.get(choice+8).getCommand()+"\n");
        calls.get(choice+8).call();
    }

    private static void reportMenu(){
        int choice=0;
        System.out.println("The possible delete commands are:");
        for (int i=12; i<17; i++) {
            System.out.println(calls.get(i).getCommand());
        }
        System.out.println("Please choose a command:");
        try {
            choice = Integer.parseInt(input.nextLine());
        }
        catch (Exception e){
            System.out.println("illegal input");
            return;
        }
        if(choice>5| choice<1){
            System.out.println("no such command");
            return;
        }
        System.out.println("you have chosen the command:");
        System.out.println(calls.get(choice+11).getCommand()+"\n");
        calls.get(choice+11).call();
    }

    private static void initData()
    {
        List<InterfaceWorker> workers=init_workers();
        List<InterfaceShift> shifts=init_shifts();
        List<InterfaceConstraint> constraints=init_constraints();
        for (InterfaceWorker worker:workers)
            //DeliveryAndWorkers.Interface.addEmployee(worker);
        for (InterfaceShift shift:shifts)
            Interface.addShift(shift);
        for (InterfaceConstraint constraint:constraints)
            Interface.addConstraint(constraint);
    }

    private static List<InterfaceConstraint> init_constraints() {
        List<InterfaceConstraint> constraints=new LinkedList<>();
        try {
            InterfaceConstraint c1=new InterfaceConstraint(new SimpleDateFormat("dd/MM/yyyy").parse("30/04/2020"), false,  1, "wedding" );
            InterfaceConstraint c2=new InterfaceConstraint(new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020"), true,  1, "surgery" );
            InterfaceConstraint c3=new InterfaceConstraint(new SimpleDateFormat("dd/MM/yyyy").parse("19/6/2020"), true,  2, "doctor" );
            InterfaceConstraint c4=new InterfaceConstraint(new SimpleDateFormat("dd/MM/yyyy").parse("7/5/2020"), false,  3, "vacation" );
            InterfaceConstraint c5=new InterfaceConstraint(new SimpleDateFormat("dd/MM/yyyy").parse("7/5/2020"), true,  3, "vacation" );
            InterfaceConstraint c6=new InterfaceConstraint(new SimpleDateFormat("dd/MM/yyyy").parse("8/5/2020"), false,  3, "vacation" );
            InterfaceConstraint c7=new InterfaceConstraint(new SimpleDateFormat("dd/MM/yyyy").parse("8/5/2020"), true,  3, "vacation" );
            constraints.add(c1);
            constraints.add(c2);
            constraints.add(c3);
            constraints.add(c4);
            constraints.add(c5);
            constraints.add(c6);
            constraints.add(c7);
        } catch (ParseException e) { }
        return constraints;
    }

    private static List<InterfaceWorker> init_workers()
    {
        List<InterfaceWorker> workers=new LinkedList<>();
        try
        {
            InterfaceWorker w1 = new InterfaceWorker("avi levy", 1, 202, 2000, 10, 10, 10, new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2015"), "manager");
            InterfaceWorker w2 = new InterfaceWorker("shimon cohen", 2, 311, 1000, 10, 10, 10, new SimpleDateFormat("dd/MM/yyyy").parse("15/06/2018"), "manager");
            InterfaceWorker w3 = new InterfaceWorker("dan panorama", 3, 157, 1700, 10, 10, 10, new SimpleDateFormat("dd/MM/yyyy").parse("12/01/2019"), "cashier");
            InterfaceWorker w4 = new InterfaceWorker("ben biton", 4, 802, 900, 10, 10, 10, new SimpleDateFormat("dd/MM/yyyy").parse("14/01/2020"), "cashier");
            InterfaceWorker w5 = new InterfaceWorker("avi bitter", 5, 171, 1300, 10, 10, 10, new SimpleDateFormat("dd/MM/yyyy").parse("18/03/2020"), "driver");
            workers.add(w1);
            workers.add(w2);
            workers.add(w3);
            workers.add(w4);
            workers.add(w5);
        }catch(Exception ignored){}
        return workers;
    }

    private static List<InterfaceShift> init_shifts()
    {
        List<InterfaceShift> shifts=new LinkedList<>();
        try
        {
            List<Integer> workers_in_shift=new LinkedList<>();
            workers_in_shift.add(1);
            workers_in_shift.add(3);
            workers_in_shift.add(5);
            InterfaceShift s1=new InterfaceShift(new SimpleDateFormat("dd/MM/yyyy").parse("14/08/2020"),true,1,workers_in_shift, "Super Lee");

            workers_in_shift=new LinkedList<>();
            workers_in_shift.add(2);
            workers_in_shift.add(4);
            InterfaceShift s2=new InterfaceShift(new SimpleDateFormat("dd/MM/yyyy").parse("14/08/2020"),false,1,workers_in_shift,"Super Lee");

            workers_in_shift=new LinkedList<>();
            workers_in_shift.add(1);
            workers_in_shift.add(5);
            InterfaceShift s3=new InterfaceShift(new SimpleDateFormat("dd/MM/yyyy").parse("16/08/2020"),true,1,workers_in_shift,"Super Lee");

            workers_in_shift=new LinkedList<>();
            workers_in_shift.add(1)
            ;workers_in_shift.add(2); workers_in_shift.add(3); workers_in_shift.add(4);workers_in_shift.add(5);
            InterfaceShift s4=new InterfaceShift(new SimpleDateFormat("dd/MM/yyyy").parse("19/10/2020"),false,1,workers_in_shift,"Super Lee");

            workers_in_shift=new LinkedList<>();
            workers_in_shift.add(2);
            workers_in_shift.add(3);
            InterfaceShift s5=new InterfaceShift(new SimpleDateFormat("dd/MM/yyyy").parse("19/10/2020"),true,1,workers_in_shift,"Super Lee");

            shifts.add(s1);
            shifts.add(s2);
            shifts.add(s3);
            shifts.add(s4);
            shifts.add(s5);
        }
        catch (Exception ignored){}
        return shifts;
    }

    private static void initCalls(List<Callback> calls){

        //add new employee.
        calls.add(new Callback() {
            @Override
            public void call() {
                boolean finish=false;
                String lic;
                int i=0;
                List<String> branches=Interface.getBranches();
                List<String> licenses=new LinkedList<>();
                InterfaceWorker w=new InterfaceWorker();
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
                    System.out.print("startDate(dd/MM/yyyy): ");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    w.setStart_date(format.parse(input.nextLine()));
                    System.out.print("role: ");
                    w.setRole(input.nextLine());
                    System.out.print("bank account: ");
                    w.setBank_account_number(Integer.parseInt(input.nextLine()));
                    System.out.println("please choose a branch from the list:");
                    for(String branch: branches){
                        System.out.println(i+") "+branch);
                        i++;
                    }
                    w.setBranchAddress(branches.get(Integer.parseInt(input.nextLine())));
                    if(w.getRole().toLowerCase().equals("driver")){
                        while (!finish) {
                            System.out.println("please enter the license the driver has:");
                            lic = input.nextLine();
                            licenses.add(lic);
                            System.out.println("Do you want to enter more licenses? (y/n)");
                            if (input.nextLine().equals("n"))
                                finish = true;
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("wrong parameter has been entered, exiting back to main menu");
                    return;
                }

                System.out.println(Interface.addEmployee(w, licenses));
        }});

        //add new shift

        calls.add(new Callback() {
            @Override
            public void call() {
                int i=0;
                List<String> branches=Interface.getBranches();
                InterfaceShift shift = new InterfaceShift();
                boolean finish = false;
                String role;
                try {
                    System.out.println("please enter the details of the new shift");
                    System.out.print("date(dd/MM/yyyy): ");
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    shift.setDate(format.parse(input.nextLine()));
                    System.out.print("is it a Morning shift? (true/false)");
                    shift.setMorning(Boolean.parseBoolean(input.nextLine()));
                    System.out.println("please choose a branch from the list:");
                    for(String branch: branches){
                        System.out.println(i+") "+branch);
                        i++;
                    }
                    shift.setBranchAddress(branches.get(Integer.parseInt(input.nextLine())));
                    System.out.println("The available managers are:");
                    String ans = Interface.getWorkersByRole("manager", shift.getBranchAddress(), shift.getDate(), shift.isMorning());
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
                        ans=Interface.getWorkersByRole(role, shift.getBranchAddress(), shift.getDate(), shift.isMorning());
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
                InterfaceConstraint c=new InterfaceConstraint();
                try {
                    System.out.println("please enter the details of the new constraint");
                    System.out.print("Employee's id: ");
                    c.setId(Integer.parseInt(input.nextLine()));
                    System.out.print("Date(dd/MM/yyyy): ");
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
                int i=0;
                List<String> branches=Interface.getBranches();
                InterfaceWorker w;
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
                        System.out.println("9. branch");
                        System.out.println("10. finished editing");
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
                                System.out.print("Enter new start date(dd/MM/yyyy):");
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
                                System.out.println("please choose a branch from the list:");
                                for(String branch: branches){
                                    System.out.println(i+") "+branch);
                                    i++;
                                }
                                w.setBranchAddress(branches.get(Integer.parseInt(input.nextLine())));
                                break;
                            case 10:
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
                int i=0;
                List<String> branches=Interface.getBranches();
                String oldBranch;
                InterfaceShift s;
                Date oldDate;
                boolean isMorning;
                int opt=0;
                boolean finished=false;
                try {
                    System.out.println("please enter the Date of the shift you want to edit (dd/MM/yyyy):");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    System.out.println("please choose the old branch from the list:");
                    for(String branch: branches){
                        System.out.println(i+") "+branch);
                        i++;
                    }
                    oldBranch=branches.get(Integer.parseInt(input.nextLine()));
                    s=Interface.searchShift(oldDate,isMorning,oldBranch);
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
                                System.out.print("Enter new start date(dd/MM/yyyy):");
                                format.setLenient(false);
                                s.setDate(format.parse(input.nextLine()));
                                break;
                            case 2:
                                System.out.print("is it a morning shift? (true/false)");
                                s.setMorning(Boolean.parseBoolean(input.nextLine()));
                                break;
                            case 3:
                                String ans=Interface.getWorkersByRole("manager",s.getBranchAddress(), s.getDate(),s.isMorning());
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
                                Integer id= Integer.parseInt(input.nextLine());
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

                System.out.println(Interface.editShift(s,oldDate,isMorning, oldBranch));
            }});


        //edit constraint.
        calls.add(new Callback() {
            @Override
            public void call() {
                InterfaceConstraint c;
                List<InterfaceConstraint> constraints;
                Date oldDate;
                boolean isMorning;
                int id;
                int opt=0;
                boolean finished=false;
                try {
                    System.out.println("please enter the employee's id who's constraint you want to edit");
                    id=Integer.parseInt(input.nextLine());
                    System.out.println("please enter the Date of the constraint you want to edit (dd/MM/yyyy):");
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
                    for(InterfaceConstraint con: constraints){
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
                                System.out.print("Enter new start date:(dd/MM/yyyy)");
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
                InterfaceWorker w;
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
                int i=0;
                List<String> branches=Interface.getBranches();
                String oldBranch;
                InterfaceShift s;
                Date oldDate;
                boolean isMorning;
                String ans;
                try {
                    System.out.println("please enter the Date of the shift you want to delete (dd/MM/yyyy):");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    System.out.println("please choose the shift's branch from the list:");
                    for(String branch: branches){
                        System.out.println(i+") "+branch);
                        i++;
                    }
                    oldBranch=branches.get(Integer.parseInt(input.nextLine()));
                    s=Interface.searchShift(oldDate,isMorning, oldBranch);
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
                InterfaceConstraint c;
                List<InterfaceConstraint> constraints;
                Date oldDate;
                boolean isMorning;
                int id;
                String ans;
                try {
                    System.out.println("please enter the employee's id who's constraint you want to delete");
                    id=Integer.parseInt(input.nextLine());
                    System.out.println("please enter the Date of the constraint you want to delete (dd/MM/yyyy):");
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
                    for(InterfaceConstraint con: constraints){
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
                InterfaceWorker w;
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
                int i=0;
                List<String> branches=Interface.getBranches();
                String oldBranch;
                InterfaceShift s;
                Date oldDate;
                boolean isMorning;
                String ans;
                try {
                    System.out.println("please enter the Date of the shift you want to search (dd/MM/yyyy):");
                    SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    oldDate=format.parse(input.nextLine());
                    System.out.println("is it a morning shift? (true/false)");
                    isMorning=Boolean.parseBoolean(input.nextLine());
                    System.out.println("please choose the shift's branch from the list:");
                    for(String branch: branches){
                        System.out.println(i+") "+branch);
                        i++;
                    }
                    oldBranch=branches.get(Integer.parseInt(input.nextLine()));
                    s=Interface.searchShift(oldDate,isMorning, oldBranch);
                    s=Interface.searchShift(oldDate,isMorning, oldBranch);
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
                InterfaceConstraint c;
                List<InterfaceConstraint> constraints;
                Date oldDate;
                boolean isMorning;
                int id;
                String ans;
                try {
                    System.out.println("please enter the employee's id who's constraint you want to search");
                    id=Integer.parseInt(input.nextLine());
                    System.out.println("please enter the Date of the constraint you want to search (dd/MM/yyyy):");
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
                    for(InterfaceConstraint con: constraints){
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

        //print all employees
        calls.add(new Callback() {
            @Override
            public void call() {
                System.out.println(Interface.printEmployees());
            }
        });

        //print all shifts
        calls.add(new Callback() {
            @Override
            public void call() {
                System.out.println(Interface.printShifts());
            }
        });

        //print all constraints
        calls.add(new Callback() {
            @Override
            public void call() {
                System.out.println(Interface.printConstraints());
            }
        });

    }
    private static void initActions(List<Callback> actions){
        actions.get(0).setCommand("1. add new employee.");
        actions.get(1).setCommand("2. add new shift.");
        actions.get(2).setCommand("3. add new constraint.");
        actions.get(3).setCommand("1. edit employee.");
        actions.get(4).setCommand("2. edit shift.");
        actions.get(5).setCommand("3. edit constraint.");
        actions.get(6).setCommand("1. delete employee.");
        actions.get(7).setCommand("2. delete shift.");
        actions.get(8).setCommand("3. delete constraint.");
        actions.get(9).setCommand("1. search employee.");
        actions.get(10).setCommand("2. search shift.");
        actions.get(11).setCommand("3. search constraint.");
        actions.get(12).setCommand("1. present weekly shift report.");
        actions.get(13).setCommand("2. present weekly constraint report.");
        actions.get(14).setCommand("3. print all employees.");
        actions.get(15).setCommand("4. print all shifts.");
        actions.get(16).setCommand("5. print all constraints.");
    }
}


