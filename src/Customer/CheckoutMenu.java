package Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CheckoutMenu extends JFrame implements ActionListener {
    Connection con;

    {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    String cat,table;
    double Total;
    ArrayList<Object[]> Cart;
    JButton del,Place;
    JTable Product;
    String username;
    CheckoutMenu(ArrayList<Object[]> C,double t,String c,String str) {
        cat = c;
        Cart=C;
        Total=t;
        username=str;
        setTitle("Chekout");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(700, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        ImageIcon mainlogo = new ImageIcon("images\\mainlogo.png");
        setIconImage(mainlogo.getImage());

        JPanel head = new JPanel();
        head.setBounds(0, 0, 700, 100);
        head.setBackground(new Color(44, 62, 80));
        head.setLayout(null);
        JLabel heading = new JLabel("Items in Cart:");
        heading.setFont(new Font("Futura", Font.BOLD, 35));
        heading.setBounds(30, 25, 400, 50);
        heading.setForeground(Color.white);
        head.add(heading);

        String[] col = {"No.", "Name", "Price (Rs.)", "Quantity", "Total"};

        DefaultTableModel Tb = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Product = new JTable(Tb);
        for (Object[] objects : Cart) {
            Object[] obj={objects[0],objects[1],objects[2],objects[3],objects[4]};
            Tb.addRow(obj);
        }
        Product.setFont(new Font("Futura", Font.PLAIN, 14));
        Product.setRowHeight(30);
        TableColumnModel columnModel = Product.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(150);


        JScrollPane body = new JScrollPane(Product);
        body.setBounds(1, 100, 687, 300);
        body.setBackground(new Color(234, 250, 241));
        JPanel T=new JPanel(null);
        T.setBounds(0,400,700,100);
        T.setBackground(new Color(208, 211, 212));
        JLabel tname=new JLabel("Total:");
        tname.setForeground(new Color(52, 73, 94));
        tname.setFont(new Font("Rockwell",Font.BOLD,30));
        tname.setBounds(210,20,120,60);
        JLabel total=new JLabel(Total+" Rs.");
        total.setForeground(new Color(40, 180, 99));
        total.setFont(new Font("Myriad",Font.BOLD,30));
        total.setBounds(330,20,150,60);

        T.add(tname);
        T.add(total);



        JPanel footer = new JPanel();
        footer.setBounds(0, 500, 700, 100);
        footer.setBackground(new Color(86, 101, 115));
        footer.setLayout(null);

        JButton back = new JButton("Cancel");
        back.setForeground(Color.white);
        back.setBackground(new Color(203, 67, 53));
        back.setBounds(580, 20, 90, 30);
        back.setFocusable(false);
        back.setFont(new Font("Tahoma", Font.PLAIN, 12));
        back.addActionListener(E -> {
            int opt=JOptionPane.showConfirmDialog(null,"This will remove all items from your cart?","Warning",JOptionPane.OK_CANCEL_OPTION);
            if(opt==0) {
                new ShopMenu(cat, username);
                dispose();
            }
        });
        footer.add(back);

        del = new JButton("Remove");
        del.setForeground(Color.white);
        del.setBackground(new Color(230, 126, 34));
        del.setBounds(30, 20, 90, 30);
        del.setFocusable(false);
        del.setFont(new Font("Didot", Font.PLAIN, 12));
        del.addActionListener(this);
        footer.add(del);

        Place = new JButton("Place Order");
        Place.setForeground(Color.white);
        Place.setBackground(new Color(22, 160, 133));
        Place.setBounds(275, 10, 150, 40);
        Place.setFocusable(false);
        Place.setFont(new Font("Didot", Font.BOLD, 16));
        Place.addActionListener(this);
        footer.add(Place);


        setVisible(true);
        add(head);
        add(body);
        add(T);
        add(footer);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==del){
            int opt=JOptionPane.showConfirmDialog(null,"Remove Item from Cart?","Sure?",JOptionPane.YES_NO_OPTION);
            if(opt==0){
                int t= (int) Cart.get(Product.getSelectedRow())[4];
                Cart.remove(Product.getSelectedRow());
                Total-=t;
                if(Cart.isEmpty()){
                    new ShopMenu(cat, username);
                    dispose();
                }
                else {
                    new CheckoutMenu(Cart, Total, cat, username);
                    dispose();
                }
            }
        }
        if(e.getSource()==Place){
            int opt=JOptionPane.showConfirmDialog(null,"You want to Place Order?","Sure?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(opt==0){
                StringBuilder items= new StringBuilder();
                for(Object[] i:Cart){
                    try {
                        table = switch (cat) {
                            case "Beverages" -> "Beverage";
                            case "Snacks" -> "snack";
                            case "Bakery Items" -> "Bakery_item";
                            default -> "fruit";
                        };
                        Statement q=con.createStatement();
                        ResultSet rs=q.executeQuery("select stock from "+table+" where product_id = '"+i[5]+"'");
                        if (rs.next()) {

                            PreparedStatement ps = con.prepareStatement("update "+table+" set stock=? "
                                    +"where product_id = ?");
                            ps.setInt(1,rs.getInt(1)-(int)i[3]);
                            ps.setString(2,(String)i[5]);

                            ps.executeUpdate();
                        }
                        items.append(i[1]).append(", ");
                    }catch (SQLException throwables){
                        throwables.printStackTrace();
                    }
                }

                try {
                    int id=0;
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery("Select MAX(order_id) from orders");
                    if(rs.next()){
                        id=rs.getInt(1)+1;
                    }
                    PreparedStatement query = con.prepareStatement("insert into orders(items,total,status,order_id,customer_username)"
                            + "values(?,?,?,?,?)");
                    query.setString(1,items.toString() );
                    query.setDouble(2, Total);
                    query.setBoolean(3, false);
                    query.setInt(4, id);
                    query.setString(5, username);

                    query.execute();
                    con.close();

                    JOptionPane.showMessageDialog(null,"Order Placed Sucessfully","Mashallah",JOptionPane.INFORMATION_MESSAGE);
                    new CustomerMenu(username);
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
