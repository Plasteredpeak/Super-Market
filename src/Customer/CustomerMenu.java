package Customer;

import Profiling.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustomerMenu extends JFrame implements ActionListener {
    Connection con;

    {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    JButton Shop,Orders,Complain,R;
    String username;
    public CustomerMenu(String str){
        username=str;
        try {
            Statement q = con.createStatement();
            ResultSet rs = q.executeQuery("select * from customer where username = '" + username + "'");
            if (rs.next()) {
                setTitle(rs.getString(7));
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                setResizable(false);
                setSize(600, 500);
                setLayout(null);
                setLocationRelativeTo(null);
                ImageIcon mainlogo = new ImageIcon("images\\mainlogo.png");
                setIconImage(mainlogo.getImage());

                JPanel head = new JPanel(null);
                head.setBounds(0, 0, 600, 100);
                head.setBackground(new Color(44, 62, 80));
                JLabel heading = new JLabel(rs.getString(1) + " " + rs.getString(2));
                heading.setFont(new Font("Rooney", Font.BOLD, 30));
                heading.setBounds(15, 25, 200, 50);
                heading.setForeground(Color.white);
                JLabel email = new JLabel(rs.getString(5));
                email.setFont(new Font("Rooney", Font.PLAIN, 16));
                email.setBounds(400, 50, 300, 50);
                email.setForeground(Color.white);

                head.add(heading);
                head.add(email);


                JPanel body = new JPanel(null);
                body.setBounds(0, 100, 600, 400);
                body.setBackground(new Color(213, 245, 227));

                Shop = new JButton("Shop");
                Shop.setFocusable(false);
                Shop.setBounds(200, 50, 200, 65);
                Shop.setFont(new Font("Bodoni", Font.BOLD, 18));
                Shop.setBackground(new Color(40, 55, 71));
                Shop.setForeground(Color.white);
                Shop.setBorder(BorderFactory.createEtchedBorder());
                Shop.addActionListener(this);

                Orders = new JButton("Check Orders");
                Orders.setFocusable(false);
                Orders.setBounds(200, 130, 200, 65);
                Orders.setFont(new Font("Bodoni", Font.BOLD, 18));
                Orders.setBackground(new Color(40, 55, 71));
                Orders.setForeground(Color.white);
                Orders.setBorder(BorderFactory.createEtchedBorder());
                Orders.addActionListener(this);

                Complain = new JButton("Complain");
                Complain.setFocusable(false);
                Complain.setBounds(200, 210, 200, 65);
                Complain.setFont(new Font("Bodoni", Font.BOLD, 18));
                Complain.setBackground(new Color(40, 55, 71));
                Complain.setForeground(Color.white);
                Complain.setBorder(BorderFactory.createEtchedBorder());
                Complain.addActionListener(this);


                JButton back = new JButton("Log Out");
                back.setForeground(Color.white);
                back.setBackground(new Color(203, 67, 53));
                back.setBounds(470, 300, 90, 40);
                back.setFocusable(false);
                back.setFont(new Font("Tahoma", Font.BOLD, 14));
                back.addActionListener(E -> {
                    new MainMenu();
                    dispose();
                });

                R = new JButton("Delete Acc");
                R.setForeground(Color.white);
                R.setBackground(new Color(175, 96, 26));
                R.setBounds(30, 300, 100, 40);
                R.setFocusable(false);
                R.setFont(new Font("Tahoma", Font.BOLD, 12));
                R.addActionListener(this);

                body.add(back);
                body.add(Shop);
                body.add(Orders);
                body.add(Complain);
                body.add(R);

                setVisible(true);
                add(head);
                add(body);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==R) {
            int opt = JOptionPane.showConfirmDialog(null, "Are u sure you want to Delete your Account?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (opt == 0) {
                try {
                    Statement query = con.createStatement();
                    query.executeQuery("delete from customer where username = '" + username + "'");
                    JOptionPane.showMessageDialog(null, "Account Deleted!!", "Why._.", JOptionPane.INFORMATION_MESSAGE);
                    new MainMenu();
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        if(e.getSource()==Shop){
            String[] C={"Beverages","Bakery Items","Snacks","Fruits & Vegetables"};
            String opt=(String)JOptionPane.showInputDialog(null,"Pick a Category","Choose",JOptionPane.QUESTION_MESSAGE,null,C,C[0]);
            if(opt != null) {
                new ShopMenu(opt,username);
                dispose();
            }
        }
        if(e.getSource()== Orders) {
            try {
                Statement q = con.createStatement();
                ResultSet rs= q.executeQuery("select * from orders where customer_username = '" + username + "'");

                if(rs.next()){
                    new OrdersMenu(username);
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "No orders Found", "Bruh", JOptionPane.WARNING_MESSAGE);
                }catch (SQLException throwables){
                    throwables.printStackTrace();
                    }
        }
        if(e.getSource()==Complain){
            String msg=JOptionPane.showInputDialog(null,"Enter Message","Complain",JOptionPane.INFORMATION_MESSAGE);
            if(msg!=null){
                try {
                    int id=0;
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery("Select MAX(query_id) from customer_query");
                    if(rs.next()){
                        id=rs.getInt(1)+1;
                    }
                    PreparedStatement query = con.prepareStatement("insert into customer_query(msg,query_id,customer_username)"
                            + "values(?,?,?)");
                    query.setString(1,msg);
                    query.setInt(2,id);
                    query.setString(3,username);

                    query.execute();

                    JOptionPane.showMessageDialog(null,"Complain sent to Employees :)","Karen",JOptionPane.INFORMATION_MESSAGE);


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
