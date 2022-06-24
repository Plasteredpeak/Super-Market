package Profiling;

import Attributes.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;
import java.util.regex.Pattern;

public class Register extends JFrame implements ActionListener {
    String uname,pass,user;
    JButton reg,sug;
    JTextField fNAme,Lname,U,Pa,age,ph,email;
    JRadioButton male,female;
    JComboBox phone,day,month,year;
    Register(String n,String p,String u){
        user=u;
        uname=n;
        pass=p;
        setTitle(user);
        ImageIcon mainlogo=new ImageIcon("images\\mainlogo.png");
        setIconImage(mainlogo.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(700,600);
        setLayout(null);
        setLocationRelativeTo(null);

        JPanel head=new JPanel(null);
        head.setBounds(0,0,700,100);
        head.setBackground(new Color(169, 223, 191));
        JLabel heading=new JLabel("Registeration");
        heading.setFont(new Font("Rooney",Font.BOLD,40));
        heading.setBounds(15,25,300,50);

        JButton main =new JButton("Main Menu");
        main.setForeground(Color.white);
        main.setBackground(new Color(99, 57, 116));
        main.setBounds(555,50,120,40);
        main.setFocusable(false);
        main.setFont(new Font("Tahoma",Font.BOLD,14));
        main.addActionListener(E -> {new MainMenu();dispose();});

        head.add(heading);
        head.add(main);

        JPanel body=new JPanel(null);
        body.setBounds(0,100,700,500);
        body.setBackground(new Color(33, 47, 61));

        JLabel fn=new JLabel("First name:");
        fn.setForeground(Color.white);
        fn.setFont(new Font("Roomey",Font.PLAIN,14));
        fn.setBounds(30,30,100,50);
        fNAme=new JTextField();
        fNAme.setBounds(120,40,100,23);
        fNAme.setFont(new Font("helvetica",Font.PLAIN,14));
        fNAme.setBorder(BorderFactory.createEtchedBorder());

        JLabel ln=new JLabel("Last name:");
        ln.setForeground(Color.white);
        ln.setFont(new Font("Roomey",Font.PLAIN,14));
        ln.setBounds(240,30,100,50);
        Lname=new JTextField();
        Lname.setBounds(320,40,100,23);
        Lname.setFont(new Font("helvetica",Font.PLAIN,14));
        Lname.setBorder(BorderFactory.createEtchedBorder());

        JLabel un=new JLabel("User name:");
        un.setForeground(Color.white);
        un.setFont(new Font("Roomey",Font.PLAIN,14));
        un.setBounds(30,80,100,50);
        U=new JTextField();
        U.setBounds(120,90,170,23);
        U.setFont(new Font("helvetica",Font.PLAIN,14));
        U.setBorder(BorderFactory.createEtchedBorder());
        U.setText(uname);

        JLabel P=new JLabel("Password:");
        P.setForeground(Color.white);
        P.setFont(new Font("Roomey",Font.PLAIN,14));
        P.setBounds(30,130,100,50);
        Pa=new JTextField();
        Pa.setBounds(120,140,170,23);
        Pa.setFont(new Font("helvetica",Font.PLAIN,14));
        Pa.setText(pass);
        Pa.setBorder(BorderFactory.createEtchedBorder());

        JLabel a=new JLabel("Age:");
        a.setForeground(Color.white);
        a.setFont(new Font("Roomey",Font.PLAIN,14));
        a.setBounds(30,180,100,50);
        age=new JTextField();
        age.setBounds(120,190,70,23);
        age.setFont(new Font("helvetica",Font.PLAIN,14));
        age.setBorder(BorderFactory.createEtchedBorder());

        JLabel pn=new JLabel("Phone no:");
        pn.setForeground(Color.white);
        pn.setFont(new Font("Roomey",Font.PLAIN,14));
        pn.setBounds(30,230,100,50);
        String[] c={"+92","+1","+44","+61"};
        phone=new JComboBox(c);
        phone.setBounds(120,240,50,23);
        ph=new JTextField();
        ph.setBounds(180,240,130,23);
        ph.setFont(new Font("helvetica",Font.PLAIN,14));
        ph.setBorder(BorderFactory.createEtchedBorder());

        JLabel e=new JLabel("Email:");
        e.setForeground(Color.white);
        e.setFont(new Font("Roomey",Font.PLAIN,14));
        e.setBounds(30,280,100,50);
        email=new JTextField();
        email.setBounds(120,290,170,23);
        email.setFont(new Font("helvetica",Font.PLAIN,14));
        email.setBorder(BorderFactory.createEtchedBorder());

        JLabel d=new JLabel("Date of Birth:");
        d.setForeground(Color.white);
        d.setFont(new Font("Roomey",Font.PLAIN,14));
        d.setBounds(30,330,100,50);
        String[] D=new String[31];
        String[] M=new String[12];
        String[] Y=new String[31];
        Random r=new Random();
        for(int i=0;i<D.length;i++){
            if(i<12) {
                M[i] = String.valueOf(i + 1);
            }
            D[i]=String.valueOf(i+1);
            Y[i]=String.valueOf(1980+i);
        }
        day=new JComboBox(D);
        month=new JComboBox(M);
        year=new JComboBox(Y);
        day.setBounds(140,340,50,23);
        month.setBounds(200,340,50,23);
        year.setBounds(260,340,70,23);
        year.addActionListener(this);
        day.addActionListener(this);
        month.addActionListener(this);

        ImageIcon s=new ImageIcon("images\\select.png");
        ImageIcon us=new ImageIcon("images\\unselected.png");
        male=new JRadioButton("Male");
        male.setBounds(220,180,100,50);
        male.setBackground(new Color(33, 47, 61));
        male.setFocusable(false);
        male.setIcon(us);
        male.setSelectedIcon(s);
        male.setForeground(Color.white);
        female=new JRadioButton("Female");
        female.setBackground(new Color(33, 47, 61));
        female.setBounds(320,180,100,50);
        female.setForeground(Color.white);
        female.setFocusable(false);
        female.setIcon(us);
        female.setSelectedIcon(s);
        ButtonGroup gender=new ButtonGroup();
        gender.add(male);
        gender.add(female);
        male.addActionListener(this);
        female.addActionListener(this);

        sug=new JButton("Suggest");
        sug.setForeground(Color.black);
        sug.setBackground(new Color(214, 219, 223));
        sug.setBounds(320,90,80,22);
        sug.setFocusable(false);
        sug.setBorder(BorderFactory.createEtchedBorder());
        sug.addActionListener(this);

        reg=new JButton("Register");
        reg.setForeground(Color.black);
        reg.setBackground(new Color(169, 223, 191));
        reg.setFont(new Font("roomey",Font.BOLD,20));
        reg.setBounds(275,390,150,45);
        reg.setFocusable(false);
        reg.addActionListener(this);

        JButton back =new JButton("Back");
        back.setForeground(Color.white);
        back.setBackground(new Color(203, 67, 53));
        back.setBounds(600,410,60,30);
        back.setFocusable(false);
        back.setFont(new Font("Tahoma",Font.PLAIN,12));
        back.addActionListener(E -> {new Login(user);dispose();});



        body.add(fn);
        body.add(fNAme);
        body.add(ln);
        body.add(Lname);
        body.add(un);
        body.add(U);
        body.add(sug);
        body.add(P);
        body.add(Pa);
        body.add(a);
        body.add(age);
        body.add(e);
        body.add(email);
        body.add(pn);
        body.add(ph);
        body.add(phone);
        body.add(male);
        body.add(female);
        body.add(d);
        body.add(day);
        body.add(month);
        body.add(year);
        body.add(reg);
        body.add(back);

        setVisible(true);
        add(body);
        add(head);
    }






    @Override
    public void actionPerformed(ActionEvent e) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(e.getSource()==sug){
            Random rn=new Random();
            int n=rn.nextInt(100);
            String[] arr={fNAme.getText(),Lname.getText(),fNAme.getText()+Lname.getText(),Lname.getText()+fNAme.getText()};
            String[] duh={"Dumb","Stupid","Imbecile","Noob"};
            int i=rn.nextInt(arr.length);
            if(!fNAme.getText().equals("") || !fNAme.getText().equals(""))
                U.setText(arr[i]+n);
            else
                U.setText(duh[i]+101);
        }
        if(e.getSource()==reg){
            if(!fNAme.getText().equals("") || !fNAme.getText().equals("")){
                if(!U.getText().equals("")){
                    if(!U.getText().equals("")){
                        if(!age.getText().equals("")){
                            try{
                                int AGE=Integer.parseInt(age.getText());
                                if(male.isSelected()|| female.isSelected()){
                                    String g=(male.isSelected())?"Male":"Female";
                                    if(!ph.getText().equals("")){
                                        try{
                                            long phoneNo=Long.parseLong(ph.getText());
                                            if(String.valueOf(phoneNo).length() ==10){
                                                if(!email.getText().equals("")){
                                                    String valid = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
                                                    Pattern pat=Pattern.compile(valid);
                                                    if(pat.matcher(email.getText()).matches()){
                                                        int option=JOptionPane.showConfirmDialog(null,"Are u sure u want to register?","Confirmation",JOptionPane.OK_CANCEL_OPTION);
                                                        if(option==0) {
                                                            Date d = new Date(Integer.parseInt(day.getSelectedItem().toString()), Integer.parseInt(month.getSelectedItem().toString()), Integer.parseInt(year.getSelectedItem().toString()));
                                                            if (user.equalsIgnoreCase("Administrator")) {
                                                                try {
                                                                    PreparedStatement query = con.prepareStatement("insert into admin(first_name,last_name,age,phone_no,email,date_of_birth,gender,username,password)"
                                                                            +"values(?,?,?,?,?,?,?,?,?)" );
                                                                    query.setString(1,fNAme.getText());
                                                                    query.setString(2,Lname.getText());
                                                                    query.setInt(3,AGE);
                                                                    query.setString(4,phone.getSelectedItem().toString()+" "
                                                                            + phoneNo);
                                                                    query.setString(5,email.getText());
                                                                    query.setDate(6,java.sql.Date.valueOf(d.toString()));
                                                                    query.setString(7,g);
                                                                    query.setString(8,U.getText());
                                                                    query.setString(9,Pa.getText());

                                                                    query.execute();
                                                                    con.close();

                                                                    JOptionPane.showMessageDialog(null,"Registered Sucessfully !","Yayyyy",JOptionPane.INFORMATION_MESSAGE);
                                                                    new Login(user);
                                                                    dispose();

                                                                } catch (SQLException throwables) {
                                                                    throwables.printStackTrace();
                                                                }
                                                            }
                                                            else if (user.equalsIgnoreCase("employee")) {
                                                                boolean flag = false;
                                                                try {
                                                                    Statement st = con.createStatement();
                                                                    ResultSet rs = st.executeQuery("select * from employee where username = '" + U.getText() + "'");
                                                                    if (rs.next()) {
                                                                        JOptionPane.showMessageDialog(null, "Employee with the same username already exists", "Repitition", JOptionPane.WARNING_MESSAGE);
                                                                        flag = true;
                                                                    }
                                                                } catch (SQLException throwables) {
                                                                    throwables.printStackTrace();
                                                                }
                                                                if (!flag) {
                                                                    try {
                                                                        PreparedStatement query = con.prepareStatement("insert into employee(first_name,last_name,age,phone_no,email,date_of_birth,gender,username,password)"
                                                                                + "values(?,?,?,?,?,?,?,?,?)");
                                                                        query.setString(1, fNAme.getText());
                                                                        query.setString(2, Lname.getText());
                                                                        query.setInt(3, AGE);
                                                                        query.setString(4, phone.getSelectedItem().toString() + " "
                                                                                + phoneNo);
                                                                        query.setString(5, email.getText());
                                                                        query.setDate(6, java.sql.Date.valueOf(d.toString()));
                                                                        query.setString(7, g);
                                                                        query.setString(8, U.getText());
                                                                        query.setString(9, Pa.getText());

                                                                        query.execute();
                                                                        con.close();

                                                                        JOptionPane.showMessageDialog(null, "Registered Sucessfully !", "Yayyyy", JOptionPane.INFORMATION_MESSAGE);
                                                                        new Login(user);
                                                                        dispose();

                                                                    } catch (SQLException throwables) {
                                                                        throwables.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                            else if (user.equalsIgnoreCase("Customer")) {
                                                                boolean flag = false;
                                                                try {
                                                                    Statement st = con.createStatement();
                                                                    ResultSet rs = st.executeQuery("select * from customer where username = '" + U.getText() + "'");
                                                                    if (rs.next()) {
                                                                        JOptionPane.showMessageDialog(null, "customer with the same username already exists", "Repitition", JOptionPane.WARNING_MESSAGE);
                                                                        flag = true;
                                                                    }
                                                                } catch (SQLException throwables) {
                                                                    throwables.printStackTrace();
                                                                }
                                                                if (!flag) {
                                                                    try {
                                                                        PreparedStatement query = con.prepareStatement("insert into customer(first_name,last_name,age,phone_no,email,date_of_birth,gender,username,password)"
                                                                                + "values(?,?,?,?,?,?,?,?,?)");
                                                                        query.setString(1, fNAme.getText());
                                                                        query.setString(2, Lname.getText());
                                                                        query.setInt(3, AGE);
                                                                        query.setString(4, phone.getSelectedItem().toString() + " "
                                                                                + phoneNo);
                                                                        query.setString(5, email.getText());
                                                                        query.setDate(6, java.sql.Date.valueOf(d.toString()));
                                                                        query.setString(7, g);
                                                                        query.setString(8, U.getText());
                                                                        query.setString(9, Pa.getText());

                                                                        query.execute();
                                                                        con.close();

                                                                        JOptionPane.showMessageDialog(null, "Registered Sucessfully !", "Yayyyy", JOptionPane.INFORMATION_MESSAGE);
                                                                        new Login(user);
                                                                        dispose();

                                                                    } catch (SQLException throwables) {
                                                                        throwables.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else
                                                        JOptionPane.showMessageDialog(null,"Enter a Valid email !!!","Error 101",JOptionPane.WARNING_MESSAGE);

                                                }
                                                else
                                                    JOptionPane.showMessageDialog(null,"Enter an email!!!","Error 101",JOptionPane.ERROR_MESSAGE);

                                            }
                                            else
                                                JOptionPane.showMessageDialog(null,"Phone number can only be 10 digits!","Error 101",JOptionPane.ERROR_MESSAGE);
                                        }
                                        catch (NumberFormatException n){
                                            JOptionPane.showMessageDialog(null,"Phone number has to be a number!","Dumb 101",JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    else
                                        JOptionPane.showMessageDialog(null,"Enter a Phone number!","Error 101",JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                    JOptionPane.showMessageDialog(null,"You need to select a gender!","Error 101",JOptionPane.ERROR_MESSAGE);
                            }
                            catch (NumberFormatException n){
                                JOptionPane.showMessageDialog(null,"Age has to be a number!","Error 101",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(null,"Enter your Age!","Error 101",JOptionPane.ERROR_MESSAGE);

                    }
                    else
                        JOptionPane.showMessageDialog(null,"Enter Password!","Error 101",JOptionPane.ERROR_MESSAGE);

                }
                else
                    JOptionPane.showMessageDialog(null,"Enter UserName!!","Error 101",JOptionPane.ERROR_MESSAGE);

            }
            else
                JOptionPane.showMessageDialog(null,"Enter your name!","Error 101",JOptionPane.ERROR_MESSAGE);
        }
    }
}
