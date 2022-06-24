package Admin;

import Attributes.Date;
import Profiling.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.Random;

public class ProductEntry extends JFrame implements ActionListener {
    JTextField name,id,stock,pic,price;
    JComboBox day,month,year,cat;
    JButton enter;
    ProductEntry(){
        setTitle("Details");
        ImageIcon mainlogo=new ImageIcon("images\\mainlogo.png");
        setIconImage(mainlogo.getImage());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(500,500);
        setLayout(null);
        setLocationRelativeTo(null);

        JPanel head=new JPanel(null);
        head.setBounds(0,0,500,100);
        head.setBackground(new Color(82, 190, 128));
        JLabel heading=new JLabel("Product Details");
        heading.setFont(new Font("Gotham",Font.BOLD,25));
        heading.setBounds(15,25,200,50);

        JButton main =new JButton("Main Menu");
        main.setForeground(Color.white);
        main.setBackground(new Color(99, 57, 116));
        main.setBounds(350,50,110,40);
        main.setFocusable(false);
        main.setFont(new Font("Tahoma",Font.BOLD,13));
        main.addActionListener(E -> {new MainMenu();dispose();});

        head.add(heading);
        head.add(main);

        JPanel body=new JPanel(null);
        body.setBounds(0,100,500,400);
        body.setBackground(new Color(33, 47, 61));

        JLabel n=new JLabel("Name:");
        n.setForeground(Color.white);
        n.setFont(new Font("Roomey",Font.PLAIN,14));
        n.setBounds(30,30,100,40);
        name=new JTextField();
        name.setBounds(100,40,90,23);
        name.setFont(new Font("helvetica",Font.PLAIN,14));
        name.setBorder(BorderFactory.createEtchedBorder());

        JLabel Id=new JLabel("ID:");
        Id.setForeground(Color.white);
        Id.setFont(new Font("Roomey",Font.PLAIN,14));
        Id.setBounds(220,30,90,40);
        id=new JTextField();
        id.setBounds(260,40,90,23);
        id.setFont(new Font("helvetica",Font.PLAIN,14));
        id.setBorder(BorderFactory.createEtchedBorder());

        JLabel p=new JLabel("Price:");
        p.setForeground(Color.white);
        p.setFont(new Font("Roomey",Font.PLAIN,14));
        p.setBounds(30,90,100,40);
        price=new JTextField();
        price.setBounds(100,100,70,23);
        price.setFont(new Font("helvetica",Font.PLAIN,14));
        price.setBorder(BorderFactory.createEtchedBorder());

        JLabel c=new JLabel("Category:");
        c.setForeground(Color.white);
        c.setFont(new Font("Roomey",Font.PLAIN,14));
        c.setBounds(30,145,100,40);
        String[] C={"Beverages","Bakery Items","Snacks","Fruits & Vegetables"};
        cat=new JComboBox(C);
        cat.setBounds(120,155,180,25);

        JLabel s=new JLabel("Quantity:");
        s.setForeground(Color.white);
        s.setFont(new Font("Roomey",Font.PLAIN,14));
        s.setBounds(190,90,100,40);
        stock=new JTextField();
        stock.setBounds(270,100,70,23);
        stock.setFont(new Font("helvetica",Font.PLAIN,14));
        stock.setBorder(BorderFactory.createEtchedBorder());

        JLabel img=new JLabel("Image name:");
        img.setForeground(Color.white);
        img.setFont(new Font("Roomey",Font.PLAIN,14));
        img.setBounds(30,195,100,40);
        pic=new JTextField();
        pic.setBounds(130,205,150,23);
        pic.setFont(new Font("helvetica",Font.PLAIN,14));
        pic.setBorder(BorderFactory.createEtchedBorder());

        JLabel d=new JLabel("Date of Expiry:");
        d.setForeground(Color.white);
        d.setFont(new Font("Roomey",Font.PLAIN,14));
        d.setBounds(30,250,100,40);
        String[] D=new String[31];
        String[] M=new String[12];
        String[] Y=new String[31];
        Random r=new Random();
        for(int i=0;i<D.length;i++){
            if(i<12) {
                M[i] = String.valueOf(i + 1);
            }
            D[i]=String.valueOf(i+1);
            Y[i]=String.valueOf(2020+i);
        }
        day=new JComboBox(D);
        month=new JComboBox(M);
        year=new JComboBox(Y);
        day.setBounds(140,260,50,23);
        month.setBounds(200,260,50,23);
        year.setBounds(260,260,70,23);
        year.addActionListener(this);
        day.addActionListener(this);
        month.addActionListener(this);

        JButton back =new JButton("Back");
        back.setForeground(Color.white);
        back.setBackground(new Color(203, 67, 53));
        back.setBounds(405,315,60,30);
        back.setFocusable(false);
        back.setFont(new Font("Tahoma",Font.PLAIN,12));
        back.addActionListener(E -> {new AdminMenu();dispose();});

        enter=new JButton("Enter");
        enter.setForeground(Color.black);
        enter.setBackground(new Color(82, 190, 128));
        enter.setFont(new Font("roomey",Font.BOLD,16));
        enter.setBounds(200,305,100,40);
        enter.setFocusable(false);
        enter.addActionListener(this);

        body.add(n);
        body.add(name);
        body.add(Id);
        body.add(cat);
        body.add(id);
        body.add(price);
        body.add(p);
        body.add(c);
        body.add(pic);
        body.add(img);
        body.add(s);
        body.add(stock);
        body.add(d);
        body.add(day);
        body.add(month);
        body.add(year);
        body.add(back);
        body.add(enter);

        setVisible(true);
        add(body);
        add(head);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Connection con=null;
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(e.getSource()==enter){
            if(!name.getText().equals("")){
                if(!id.getText().equals("")){
                    if(!price.getText().equals("") || !stock.getText().equals("")){
                        try{
                            int p=Integer.parseInt(price.getText());
                            int q=Integer.parseInt(stock.getText());
                            if(!pic.getText().equals("")){
                                String img;
                                File f=new File("Images\\"+pic.getText()+".png");
                                if(f.exists()){
                                    img="Images\\"+pic.getText()+".png";
                                }
                                else{
                                    img="Images\\default.png";
                                    JOptionPane.showMessageDialog(null,"Default Image inserted!!","Not found",JOptionPane.ERROR_MESSAGE);
                                }
                                int opt=JOptionPane.showConfirmDialog(null,"Are u sure you want to Add?",name.getText(),JOptionPane.YES_NO_OPTION);
                                if(opt==0) {
                                    boolean flag=false;
                                    try {
                                        Statement st = con.createStatement();
                                        ResultSet rs = st.executeQuery("select * from product where product_id = '"+id.getText()+"'");
                                        if (rs.next()) {
                                            JOptionPane.showMessageDialog(null, "Product with same id already exists", "Repitition", JOptionPane.WARNING_MESSAGE);
                                            flag = true;
                                        }
                                        else{
                                            PreparedStatement ps=con.prepareStatement("insert into product(product_id)"+"values(?)");
                                            ps.setString(1,id.getText());
                                            ps.execute();
                                        }
                                    }catch (SQLException throwables){
                                        throwables.printStackTrace();
                                    }
                                    if(!flag) {
                                        Date d = new Date(Integer.parseInt(day.getSelectedItem().toString()), Integer.parseInt(month.getSelectedItem().toString()), Integer.parseInt(year.getSelectedItem().toString()));
                                        String table = switch (cat.getSelectedItem().toString()) {
                                            case "Beverages" -> "Beverage";
                                            case "Snacks" -> "snack";
                                            case "Bakery Items" -> "Bakery_item";
                                            default -> "fruit";
                                        };
                                        try {
                                            PreparedStatement query = con.prepareStatement("insert into "+table+"(Product_id,name,price,category,stock,expiry,img)"
                                                    + "values(?,?,?,?,?,?,?)");
                                            query.setString(1, id.getText());
                                            query.setString(2, name.getText());
                                            query.setInt(3, p);
                                            query.setString(4, cat.getSelectedItem().toString());
                                            query.setInt(5, q);
                                            query.setDate(6, java.sql.Date.valueOf(d.toString()));
                                            query.setString(7, img);

                                            query.execute();
                                            con.close();
                                            JOptionPane.showMessageDialog(null, "Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                                            new AdminMenu();
                                            dispose();
                                        }catch (SQLException throwables){
                                            throwables.printStackTrace();
                                        }
                                    }
                                }
                            }
                            else
                                JOptionPane.showMessageDialog(null,"Enter name of Image!!","Error 101",JOptionPane.ERROR_MESSAGE);
                        }
                        catch (NumberFormatException N){
                            JOptionPane.showMessageDialog(null,"Price and Quantity need to be numbers!!","Dumb 101",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Enter Price and Quantity!!","Error 101",JOptionPane.ERROR_MESSAGE);

                }
                else
                    JOptionPane.showMessageDialog(null,"Enter Product Id!!","Error 101",JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null,"Enter a name!!","Error 101",JOptionPane.ERROR_MESSAGE);
        }
    }
}
