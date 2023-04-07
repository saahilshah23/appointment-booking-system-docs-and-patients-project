import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

public class ViewPatientsGUI extends JFrame
implements ActionListener// ItemListener, ListSelectionListener 
{
  //borders
  private Border borderCenter = BorderFactory.createEmptyBorder(10,10,10,10);
  private Border borderContents = BorderFactory.createEmptyBorder(0,0,10,0);
  private Border borderList = BorderFactory.createLineBorder(Color.BLUE, 1);

    //containers
    private Box boxButtons= Box.createHorizontalBox(); //add/remove/
    private Box boxButtons2=  Box.createHorizontalBox(); //prev, next buttons
    private Box boxListName = Box.createVerticalBox();
    private Box boxListAge = Box.createVerticalBox();
    private Box boxListPres = Box.createVerticalBox();
    private JPanel contents;
    private JPanel panelCenter;
    private JPanel panelSouth;

    //components
    private JButton btnAdd;
    private JButton btnNext;
    private JButton btnPrev;
    private JButton btnRemove;

    private JLabel lblListName;
    private JLabel lblListAge;
    private JLabel lblListPres;
    private JLabel lblViewPatient;
    

    private JList<String> ListName;
    private JList<String> ListAge;
    private JList<String> ListPres;

    private final Font fondBold = new Font(Font.DIALOG, Font.BOLD, 14);
    private final Font fontPlain = new Font(Font.DIALOG, Font.PLAIN, 14);
     
    //list data:
    private String[] names = {"Ben Haras-Gummer", "Devika Vasisht", "Fernando Ramirez", "Saahil Shah"};

     private String[] ages = {"20", "20", "20" ,"20"};

     private String[] pres = {"click to view", "click to view", "click to view", "click to view"};


     //List models 
    private DefaultListModel<String> listModelName = new DefaultListModel<>();
    private DefaultListModel<String> listModelAge = new DefaultListModel<>();
    private DefaultListModel<String> listModelPres = new DefaultListModel<>();

     public ViewPatientsGUI()
     {
       super("View Patients");
       //setFonts();


       contents = new JPanel();
       contents.setBorder(borderContents);
       contents.setLayout(new BorderLayout());
       setContentPane(contents);


       //north
       JLabel lblTitle = new JLabel("View Patients", SwingConstants.CENTER);
       lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
       contents.add(lblTitle, BorderLayout.NORTH);

       //center
       panelCenter = new JPanel();
       panelCenter.setBorder(borderCenter);


       //buttons
       btnAdd = new JButton("Add");
       btnNext = new JButton("Next >");
       btnPrev = new JButton("< Previous");
       btnRemove= new JButton ("Remove");

       //Dimension dimPrevious = btnPrev.getPreferredSize();
       //setSpecificSize(btnAdd, btnPrev);
       //setSpecificSize(btnNext, btnPrev);
       //setSpecificSize(btnRemove, btnPrev);


       boxButtons.add(btnAdd);
       boxButtons.add(btnRemove);
       panelCenter.add(boxButtons);

       boxButtons2.add(btnPrev);
       boxButtons2.add(Box.createRigidArea(new Dimension(10,1)));
       boxButtons2.add(btnNext);
       panelCenter.add(boxButtons2);


       //nameList
       lblListName = new JLabel("Patient Name");
       lblListName.setAlignmentX(LEFT_ALIGNMENT);

       initNameModel();
       ListName = new JList<>(listModelName);
       ListName.setAlignmentX(LEFT_ALIGNMENT);
       ListName.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
       ListName.setBorder(borderList);

       boxListName.add(lblListName);
       panelCenter.add(boxListName);


       

       //ageList
      lblListAge = new JLabel("Age");
      lblListAge.setAlignmentX(LEFT_ALIGNMENT);

      initAgeModel();
      ListAge = new JList<>(listModelAge);
      ListAge.setAlignmentX(LEFT_ALIGNMENT);
      ListAge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      ListAge.setBorder(borderList);

       boxListAge.add(lblListAge);
       panelCenter.add(boxListAge);


       //precription list
       lblListPres = new JLabel("Prescription");
       //lblListPres.setAlignment(LEFT_ALIGNMENT);
       
       initPresModel();
       ListPres = new JList<>(listModelPres);
       ListPres.setAlignmentX(LEFT_ALIGNMENT);
       ListPres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       ListPres.setBorder(borderList);

       boxListPres.add(lblListPres);
       panelCenter.add(boxListPres);


       btnAdd.addActionListener(this);
       btnNext.addActionListener(this);
       btnPrev.addActionListener(this);
       btnRemove.addActionListener(this);
       //ListAge.addActionListener(this);
       //ListName.addActionListener(this);
       //ListPres.addActionListener(this);

       setSize(1000,1000);
       setVisible(true);
       setResizable(false);
       setLocationRelativeTo(null);
     }


     //button handlers
     public void actionPerformed(ActionEvent e)
     {
       Object source = e.getSource();
       if(source == btnAdd)
       {
         addItem();
         return;
       }

       if(source == btnNext) //to be completed
       {
          goForward(); 
          return;
       }

       if (source == btnRemove)
       {
           removeItem();
           return;
       }

       if (source == btnPrev) //to be completed
     
       {
         goBack();
          return;

       }

      }

    private void addItem(){
      //to be completed
    }

    private void removeItem(){
      //to be completed
    }

    private void goForward(){
      //to be completed
    }
    
    private void goBack(){
    
      //to be cpmpleted
    
    }

    private void initNameModel() {
      for (String s : names) {
        listModelName.addElement(s);
      }
    }

    private void initAgeModel(){
      for (String s :ages ) {
        listModelAge.addElement(s);
      }
    }

    private void initPresModel(){
      for (String s : pres) {
        listModelPres.addElement(s);
  
      }
    }   
    
    public static void main(String[] args)
    {
      ViewPatientsGUI gui = new ViewPatientsGUI();
      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
