package Admin;

import Profiling.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminMenu extends JFrame implements ActionListener {
   Connection con;

    {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    JButton AddP,showP,ShowE,Q,R;
    public AdminMenu(){
        try {
            Statement q = con.createStatement();
            ResultSet rs=q.executeQuery("select * from admin");
            if(rs.next())
                setTitle(rs.getString("username"));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(700,600);
        setLayout(null);
        setLocationRelativeTo(null);
        ImageIcon mainlogo=new ImageIcon("images\\mainlogo.png");
        setIconImage(mainlogo.getImage());

        JPanel head=new JPanel(null);
        head.setBounds(0,0,700,100);
        head.setBackground(new Color(44, 62, 80));
        JLabel heading=new JLabel(rs.getString("first_name")+" "+rs.getString("last_name"));
        heading.setFont(new Font("Rooney",Font.BOLD,40));
        heading.setBounds(15,25,300,50);
        heading.setForeground(Color.white);
        JLabel email=new JLabel(rs.getString("email"));
        email.setFont(new Font("Rooney",Font.PLAIN,16));
        email.setBounds(500,50,300,50);
        email.setForeground(Color.white);

        head.add(heading);
        head.add(email);

        JPanel body=new JPanel(null);
        body.setBounds(0,100,700,500);
        body.setBackground(new Color(213, 245, 227));

        AddP=new JButton("Add Products");
        AddP.setFocusable(false);
        AddP.setBounds(250,50,200,65);
        AddP.setFont(new Font("Bodoni",Font.BOLD,18));
        AddP.setBackground(new Color(40, 55, 71));
        AddP.setForeground(Color.white);
        AddP.setBorder(BorderFactory.createEtchedBorder());
        AddP.addActionListener(this);

        showP=new JButton("Manage Products");
        showP.setFocusable(false);
        showP.setBounds(250,140,200,65);
        showP.setFont(new Font("Bodoni",Font.BOLD,18));
        showP.setBackground(new Color(40, 55, 71));
        showP.setForeground(Color.white);
        showP.setBorder(BorderFactory.createEtchedBorder());
        showP.addActionListener(this);

        ShowE=new JButton("Manage Employees");
        ShowE.setFocusable(false);
        ShowE.setBounds(250,230,200,65);
        ShowE.setFont(new Font("Bodoni",Font.BOLD,18));
        ShowE.setBackground(new Color(40, 55, 71));
        ShowE.setForeground(Color.white);
        ShowE.setBorder(BorderFactory.createEtchedBorder());
        ShowE.addActionListener(this);

        Q=new JButton("Queries");
        Q.setFocusable(false);
        Q.setBounds(250,320,200,65);
        Q.setFont(new Font("Bodoni",Font.BOLD,18));
        Q.setBackground(new Color(40, 55, 71));
        Q.setForeground(Color.white);
        Q.setBorder(BorderFactory.createEtchedBorder());
        Q.addActionListener(this);


        JButton back =new JButton("Log Out");
        back.setForeground(Color.white);
        back.setBackground(new Color(203, 67, 53));
        back.setBounds(570,400,90,40);
        back.setFocusable(false);
        back.setFont(new Font("Tahoma",Font.BOLD,14));
        back.addActionListener(E -> {new MainMenu();dispose();});

        R =new JButton("Resign");
        R.setForeground(Color.white);
        R.setBackground(new Color(175, 96, 26));
        R.setBounds(30,400,100,40);
        R.setFocusable(false);
        R.setFont(new Font("Times New Roman",Font.BOLD,18));
        R.addActionListener(this);

        body.add(back);
        body.add(AddP);
        body.add(showP);
        body.add(ShowE);
        body.add(Q);
        body.add(R);

        setVisible(true);
        add(head);
        add(body);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==R){
           int opt= JOptionPane.showConfirmDialog(null,"Are u sure you want to resign?","Confirmation",JOptionPane.YES_NO_OPTION);
           if(opt==0) {
               try {
                   Statement q = con.createStatement();
                   q.executeQuery("delete from admin");
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
               JOptionPane.showMessageDialog(null,"You Resigned as an Admin!!","Get yourself Checked lol",JOptionPane.INFORMATION_MESSAGE);
               new MainMenu();
               dispose();
           }
        }
        if(e.getSource()==AddP){
            new ProductEntry();
            dispose();
        }
        if(e.getSource()==showP){
            String[] C={"Beverages","Bakery Items","Snacks","Fruits & Vegetables"};
           String opt=(String)JOptionPane.showInputDialog(null,"Pick a Category","Choose",JOptionPane.QUESTION_MESSAGE,null,C,C[0]);
           if(opt != null) {
               new ShowProducts(opt);
               dispose();
           }
        }
        if(e.getSource()==ShowE){
            new ShowEmployees();
            dispose();
        }
        if(e.getSource()==Q){
            try{
                Statement s=con.createStatement();
                ResultSet rs=s.executeQuery("select * from employee_query");
                if (rs.isBeforeFirst()) {
                    Object[] r = {"Close", "Delete", "Next"};

                    Print:
                    {
                        while(rs.next()) {
                            int id=rs.getInt(2);
                            int opt = JOptionPane.showOptionDialog(null, rs.getString(1), rs.getString(3),
                                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, r, 0);
                            if (opt == 0 || opt == -1) {
                                break Print;
                            }
                            else if (opt == 1) {
                                Statement st=con.createStatement();
                                st.executeQuery("delete from employee_query where query_id ="+id);
                            }
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "No Queries Found", "gg", JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }


    }
}
