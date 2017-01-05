//Bryan Travers

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;


public class main_code extends JFrame
{
    //Build UI for menus
    protected JFrame main_frame=this;
    
    protected double[] high_scores=new double[3];
    protected int score=0;
    protected int question_index=0;
    protected final int total_questions=21;
    protected ArrayList<String[]> all_questions=new ArrayList<String[]>();
    
    protected JPanel top_text_panel=new JPanel(new GridLayout(0,1));
    protected JLabel score_label=new JLabel("", SwingConstants.RIGHT);
    protected JLabel question_num=new JLabel("Question "+question_index, SwingConstants.CENTER);
    protected JLabel question=new JLabel("Temp", SwingConstants.CENTER);
    
    
    //For Main Menu UI
    
    protected Font main_menu_font=new Font("Sanserif", Font.BOLD, 20);
    protected Font math_font=new Font("Serif", Font.BOLD, 30);
    
    protected JPanel menu_main_panel=new JPanel(new BorderLayout());
    
    protected JLabel menu_text=new JLabel("Main Menu", SwingConstants.CENTER);
    protected JLabel math_text=new JLabel("Math Test", SwingConstants.CENTER);
    
    protected JPanel menu_top_panel=new JPanel(new GridLayout(0,1));
    protected JPanel test_choices=new JPanel(new FlowLayout());
    protected JPanel typed_test_panel=new JPanel(new GridLayout(0,1));
    protected JPanel m_choice_test_panel=new JPanel(new GridLayout(0,1));
    protected JPanel matching_test_panel=new JPanel(new GridLayout(0,1));
    
    protected JButton typed_test_button=new JButton("Typed Test");
    protected JButton m_choice_test_button=new JButton("Multiple Choice\nTest");
    protected JButton matching_test_button=new JButton("Matching Test");
    
    protected JLabel high_score_label_1=new JLabel("High-Score:", SwingConstants.CENTER);
    protected JLabel high_score_label_2=new JLabel("High-Score:", SwingConstants.CENTER);
    protected JLabel high_score_label_3=new JLabel("High-Score:", SwingConstants.CENTER);
    protected JLabel typed_high_score=new JLabel("0.0%", SwingConstants.CENTER);
    protected JLabel m_choice_high_score=new JLabel("0.0%", SwingConstants.CENTER);
    protected JLabel matching_high_score=new JLabel("0.0%", SwingConstants.CENTER);
    
    
    //For typed test UI
    
    protected JPanel typed_main_panel=new JPanel(new GridLayout(0,1));
    protected JPanel typed_bottom_panel=new JPanel(new BorderLayout());
    
    protected JTextField ans_field=new JTextField(10);
    protected JButton submit_typed=new JButton("Submit");
    
    
    //For Multiple Choice UI
    
    protected JPanel m_choice_main_panel=new JPanel(new GridLayout(0,1));
    protected JPanel m_choices_panel=new JPanel(new GridLayout(0,2));
    
    protected JButton choice_a=new JButton("temp1");
    protected JButton choice_b=new JButton("temp2");
    protected JButton choice_c=new JButton("temp3");
    protected JButton choice_d=new JButton("temp4");
    
    protected MChoiceButtonListener m_choice_click_listener=new MChoiceButtonListener();
    protected int correct_ans;
    
    
    //For Matching UI
    
    protected ArrayList<Matchable_JButton> questions_for_match=new ArrayList<Matchable_JButton>();
    protected ArrayList<Matchable_JButton> ans_for_match=new ArrayList<Matchable_JButton>();
    
    protected JPanel match_main_panel=new JPanel(new BorderLayout());
    
    protected JPanel match_q_panel=new JPanel(new GridLayout(0,1,0,2));
    protected JPanel match_a_panel=new JPanel(new GridLayout(0,1,0,2));
    
    protected Matchable_Picture match_center_panel=new Matchable_Picture();
    
    protected Matchable_JButton c_clicked_match;
    protected MatchButtonListener match_click_listener=new MatchButtonListener();
    protected JButton submit_matches=new JButton("Submit");
    
    
    public main_code()
    {
        
        //Initial setup for matching Quiz
        int c=0;
        
        for (int i=6; i<12; i++){
            for (int ii=i; ii<12; ii++){
                
                String[] temp_array=new String[2];
                temp_array[0]=i+"x"+ii;
                temp_array[1]=""+(i*ii);
                
                all_questions.add(temp_array);
                
                Matchable_JButton temp_q_button=new Matchable_JButton(true, i+"x"+ii);
                questions_for_match.add(temp_q_button);
                temp_q_button.addActionListener(match_click_listener);
                
                Matchable_JButton temp_a_button=new Matchable_JButton(false, ""+(i*ii));
                ans_for_match.add(temp_a_button);
                temp_a_button.addActionListener(match_click_listener);
                
                temp_q_button.correct_match=temp_a_button;
                temp_a_button.correct_match=temp_q_button;
                
                c++;
            }
        }
        
        top_text_panel.add(score_label);
        top_text_panel.add(question_num);
        top_text_panel.add(question);
        
        
        //Initializing various Menu UI
        
        add(menu_main_panel);
        
        menu_main_panel.add(menu_top_panel, BorderLayout.NORTH);
        menu_main_panel.add(test_choices, BorderLayout.CENTER);
        
        menu_top_panel.add(math_text);
        menu_top_panel.add(menu_text);
        
        test_choices.add(typed_test_panel);
        test_choices.add(m_choice_test_panel);
        test_choices.add(matching_test_panel);
        
        typed_test_panel.add(typed_test_button);
        m_choice_test_panel.add(m_choice_test_button);
        matching_test_panel.add(matching_test_button);
        
        typed_test_panel.add(high_score_label_1);
        typed_test_panel.add(typed_high_score);
        m_choice_test_panel.add(high_score_label_2);
        m_choice_test_panel.add(m_choice_high_score);
        matching_test_panel.add(high_score_label_3);
        matching_test_panel.add(matching_high_score);
        
        math_text.setFont(math_font);
        menu_text.setFont(main_menu_font);
        
        
        //Anonymous Listener to start the Multiple Choice quiz
        m_choice_test_button.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                
                SetMChoicePart1();
                SetMChoicePart2();
                
                remove(menu_main_panel);
            }
        }
        );
        
        //Anonymous Listener to start the Matching quiz
        matching_test_button.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                
                SetUpMatching();
                remove(menu_main_panel);
            }
        }
        );
        
        //Anonymous Listener to start the Typed quiz
        typed_test_button.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                
                SetUpTypedPart1();
                SetUpTypedPart2();
                
                remove(menu_main_panel);
            }
        }
        );
        
        
        //Initializing Typed quiz answer field
        typed_bottom_panel.add(ans_field, BorderLayout.CENTER);
        typed_bottom_panel.add(submit_typed, BorderLayout.EAST);
        
        submit_typed.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent event){
                
                int num_from_feild;
                
                try{
                    num_from_feild=Integer.parseInt(ans_field.getText());
                }
                
                catch(java.lang.NumberFormatException error_name){
                    JOptionPane.showMessageDialog(main_frame, "The number you have entered is\ninvalid.");
                    return;
                }
                
                if (num_from_feild==Integer.parseInt(all_questions.get(question_index)[1])){
                    score++;
                }
                
                if (num_from_feild!=Integer.parseInt(all_questions.get(question_index)[1])){
                    JOptionPane.showMessageDialog(main_frame, "Incorrect\nCorrect answer:\n"+all_questions.get(question_index)[1]);
                }
                
                ans_field.setText("");
                question_index++;
                SetUpTypedPart2();
                
            }
        }
        );
        
        
        //Initializing Multiple Choice option buttons
        m_choices_panel.add(choice_a);
        m_choices_panel.add(choice_b);
        m_choices_panel.add(choice_c);
        m_choices_panel.add(choice_d);
        
        choice_a.addActionListener(m_choice_click_listener);
        choice_b.addActionListener(m_choice_click_listener);
        choice_c.addActionListener(m_choice_click_listener);
        choice_d.addActionListener(m_choice_click_listener);
        
        
        //Initializing Matching quiz panels
        match_main_panel.add(match_q_panel, BorderLayout.WEST);
        match_main_panel.add(match_a_panel, BorderLayout.EAST);
        match_main_panel.add(match_center_panel, BorderLayout.CENTER);
        
        match_main_panel.add(submit_matches, BorderLayout.SOUTH);
        
        //Creates the anonymous listener for when the user submits the Matching quiz
        submit_matches.addActionListener(new ActionListener(){
        
            public void actionPerformed(ActionEvent event){
                
                if (((JButton)event.getSource()).getText()=="Submit"){
                    boolean all_matched=true;
                        
                    //Check to see if the user tries to submit without matching all questions
                    for (int i=0; i<21; i++){
                        if (questions_for_match.get(i).matched_with==null){
                            all_matched=false;
                        }
                    }
                    if (all_matched==false){
                        int confirm_exit=JOptionPane.showConfirmDialog(main_frame, "Are you sure?");
                        if (confirm_exit==JOptionPane.NO_OPTION || confirm_exit==JOptionPane.CANCEL_OPTION || confirm_exit==-1){
                            return;
                        }
                    }
                    
                    ((JButton)event.getSource()).setText("Continue");
                    
                    //Show which answers were correct/incorrect
                    for (int i=0; i<21; i++){
                        
                        if (questions_for_match.get(i).matched_with==questions_for_match.get(i).correct_match){
                            questions_for_match.get(i).setBackground(Color.GREEN);
                            questions_for_match.get(i).correct_match.setBackground(Color.GREEN);
                            questions_for_match.get(i).line_color=Color.GREEN;
                            questions_for_match.get(i).correct_match.line_color=Color.GREEN;
                            score++;
                        }
                        
                        if (questions_for_match.get(i).matched_with!=questions_for_match.get(i).correct_match){
                            questions_for_match.get(i).setBackground(Color.RED);
                            questions_for_match.get(i).correct_match.setBackground(Color.RED);
                            
                            questions_for_match.get(i).setForeground(null);
                            questions_for_match.get(i).correct_match.setForeground(null);
            
                            questions_for_match.get(i).line_color=Color.RED;
                            questions_for_match.get(i).correct_match.line_color=Color.RED;
                        }
                    }
                    
                    match_center_panel.repaint();
                }
                
                //Calculates high-score related info and handles what the user wants to do after compleating the quiz
                else if (((JButton)event.getSource()).getText()=="Continue"){
                    double final_score=(double)(score*1000/21)/10.0;
                    String congrats_text="";
                    
                    if (final_score>high_scores[2]){
                        congrats_text="\nNew High-Score!";
                        matching_high_score.setText(final_score+"%");
                        high_scores[2]=final_score;
                    }
                    
                    int confirm_exit=JOptionPane.showConfirmDialog(main_frame, "Final Score:"+score+"/"+21+"\n("+final_score+"%)"+congrats_text+"\nClick Yes to exit the program\nClick No to return to the main menu\nClick Cancel to restart");
                    
                    score=0;
                    
                    if (confirm_exit==JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
                    
                    else if (confirm_exit==JOptionPane.NO_OPTION){
                        endMatching();
                        match_center_panel.repaint();
                        main_frame.remove(match_main_panel);
                        SetUpMenu();
                        return;
                    }
                    
                    else if (confirm_exit==JOptionPane.CANCEL_OPTION || confirm_exit==-1){
                        endMatching();
                        match_center_panel.repaint();
                        SetUpMatching();
                    }
                }
            }
        }
        );
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SetUpMenu();
        setVisible(true);
    }
    
    
    public void SetUpMenu(){
        //Used to build or reset the menu
        question_index=0;
        score=0;
        
        add(menu_main_panel);
        setPreferredSize(new Dimension(400, 280));
        pack();
        return;
    }
    
    
    public void SetUpTypedPart1(){
        //Used when the user first starts enters the Typed quiz
        
        Collections.shuffle(all_questions);
        
        score_label.setText("Score: "+score+"/"+total_questions);
        typed_main_panel.add(top_text_panel);
        top_text_panel.add(typed_bottom_panel);
        add(typed_main_panel);
        setPreferredSize(new Dimension(400, 200));
        pack();
        return;
    }
    
    
    public void SetUpTypedPart2(){
        //Used when the user answers a question for the Typed quiz
        
        score_label.setText("Score: "+score+"/"+total_questions);
        
        //If this is the last question. Handles high-score related info and what the user wants to do after compleating the quiz
        if (question_index==total_questions){
            double final_score=(double)(score*1000/21)/10.0;
            String congrats_text="";
                    
            if (final_score>high_scores[0]){
                congrats_text="\nNew High-Score!";
                typed_high_score.setText(final_score+"%");
                high_scores[0]=final_score;
            }
                    
            int confirm_exit=JOptionPane.showConfirmDialog(main_frame, "Final Score:"+score+"/"+21+"\n("+final_score+"%)"+congrats_text+"\nClick Yes to exit the program\nClick No to return to the main menu\nClick Cancel to restart");
                    
            if (confirm_exit==JOptionPane.YES_OPTION){
                System.exit(0);
            }
                
            question_index=0;
            score=0;
                    
            if (confirm_exit==JOptionPane.NO_OPTION){
                main_frame.remove(typed_main_panel);
                SetUpMenu();
                return;
            }
                    
            if (confirm_exit==JOptionPane.CANCEL_OPTION || confirm_exit==-1){
                SetUpTypedPart1();
                SetUpTypedPart2();
            }
            return;
        }
        
        question_num.setText("Question "+(question_index+1));
        question.setText(all_questions.get(question_index)[0]);
        
    }
    
    
    public void SetMChoicePart1(){
        //Used when the user first starts enters the Multiple Choice quiz
        
        Collections.shuffle(all_questions);
        
        top_text_panel.removeAll();
        
        top_text_panel.add(score_label);
        top_text_panel.add(question_num);
        top_text_panel.add(question);
        
        score_label.setText("Score: "+score+"/"+total_questions);
        m_choice_main_panel.add(top_text_panel);
        m_choice_main_panel.add(m_choices_panel);
        add(m_choice_main_panel);
        
        setPreferredSize(new Dimension(450, 210));
        pack();
        return;
    }
    
    
    public void SetMChoicePart2(){
        //Used when the user answers a question for the Multiple Choice quiz
        
        score_label.setText("Score: "+score+"/"+total_questions);
        
        //If the quiz is over. Handles high-score related info and what the user wants to do after compleating the quiz
        if (question_index==total_questions){
            double final_score=(double)(score*1000/21)/10.0;
                    
            String congrats_text="";
                    
            if (final_score>high_scores[1]){
                congrats_text="\nNew High-Score!";
                m_choice_high_score.setText(final_score+"%");
                high_scores[1]=final_score;
            }
                    
            int confirm_exit=JOptionPane.showConfirmDialog(main_frame, "Final Score:"+score+"/"+21+"\n("+final_score+"%)"+congrats_text+"\nClick Yes to exit the program\nClick No to return to the main menu\nClick Cancel to restart");
                
            if (confirm_exit==JOptionPane.YES_OPTION){
                System.exit(0);
            }
                
            question_index=0;
            score=0;
                    
            if (confirm_exit==JOptionPane.NO_OPTION){
                main_frame.remove(m_choice_main_panel);
                SetUpMenu();
                return;   
            }
                    
            if (confirm_exit==JOptionPane.CANCEL_OPTION || confirm_exit==-1){
                SetMChoicePart1();
                SetMChoicePart2();
            }
            return;
        }
        
        //Sets up for next question
        choice_a.setBackground(null);
        choice_b.setBackground(null);
        choice_c.setBackground(null);
        choice_d.setBackground(null);
        
        question_num.setText("Question "+(question_index+1));
        correct_ans=new Random().nextInt(3);
        int[] wrong_ans=new int[3];
        int temp_w_ans;
        
        //Generates the 3 wrong answers
        for (int i=0; i<3; i++){
            temp_w_ans=Integer.parseInt(all_questions.get(question_index)[1])-(new Random().nextInt(20)-10);
            while (temp_w_ans==wrong_ans[0] || temp_w_ans==wrong_ans[1] || temp_w_ans==wrong_ans[2] || temp_w_ans==Integer.parseInt(all_questions.get(question_index)[1])){
                temp_w_ans=Integer.parseInt(all_questions.get(question_index)[1])-(new Random().nextInt(20)-10);
            }
            wrong_ans[i]=temp_w_ans;
        }
        
        int c=0;
        
        //Places the right and wrong answers into thier correct buttons.
        if (correct_ans!=0){
            choice_a.setText(""+wrong_ans[c]);
            c++;
        }
        
        if (correct_ans==0){
            choice_a.setText(""+Integer.parseInt(all_questions.get(question_index)[1]));
        }
        
        if (correct_ans!=1){
            choice_b.setText(""+wrong_ans[c]);
            c++;
        }
        
        if (correct_ans==1){
            choice_b.setText(""+Integer.parseInt(all_questions.get(question_index)[1]));
        }
        
        if (correct_ans!=2){
            choice_c.setText(""+wrong_ans[c]);
            c++;
        }
        
        if (correct_ans==2){
            choice_c.setText(""+Integer.parseInt(all_questions.get(question_index)[1]));
        }
        
        if (correct_ans!=3){
            choice_d.setText(""+wrong_ans[c]);
            c++;
        }
        
        if (correct_ans==3){
            choice_d.setText(""+Integer.parseInt(all_questions.get(question_index)[1]));
        }
        
        question.setText(all_questions.get(question_index)[0]);
        return;
    }
    
    
    
    //The listeners for the button choices for possible answers on the Multiple choice quiz
    private class MChoiceButtonListener implements ActionListener{
        
        public void actionPerformed(ActionEvent event){
            
            if (Integer.parseInt(((JButton)event.getSource()).getText())==Integer.parseInt(all_questions.get(question_index)[1])){
                question_index++;
                score++;
                SetMChoicePart2();
                return;
            }
            
            ((JButton)event.getSource()).setBackground(Color.RED);
            
            String temp_answer="temp";
            
            //Light up the correct answer
            if (correct_ans==0){
                choice_a.setBackground(Color.GREEN);
                temp_answer=choice_a.getText();
            }
            
            if (correct_ans==1){
                choice_b.setBackground(Color.GREEN);
                temp_answer=choice_b.getText();
                
            }
            
            if (correct_ans==2){
                choice_c.setBackground(Color.GREEN);
                temp_answer=choice_c.getText();
                
            }
            
            if (correct_ans==3){
                choice_d.setBackground(Color.GREEN);
                temp_answer=choice_d.getText();
                
            }
            
            JOptionPane.showMessageDialog(main_frame, "Incorrect.\nCorrect answer:\n"+temp_answer);
            
            question_index++;
            SetMChoicePart2();
            return;
        }
    }
    
    
    public void SetUpMatching(){
        //Used when the user starts the Matching Quiz
        
        add(match_main_panel);
        match_center_panel.repaint();
        
        Collections.shuffle(questions_for_match);
        Collections.shuffle(ans_for_match);
        
        for (int i=0; i<21; i++){
            match_q_panel.add(questions_for_match.get(i));
            match_a_panel.add(ans_for_match.get(i));
        }
        
        setPreferredSize(new Dimension(640, 600));
        pack();
        return;
    }
    
    
    public void endMatching(){
        //Used when the user leaves the Matching Quiz
        
        match_q_panel.removeAll();
        match_a_panel.removeAll();
        
        submit_matches.setText("Submit");
        
        for (int i=0; i<21; i++){
            questions_for_match.get(i).matched_with=null;
            ans_for_match.get(i).matched_with=null;
            
            questions_for_match.get(i).setBackground(null);
            ans_for_match.get(i).setBackground(null);
            
            questions_for_match.get(i).line_color=Color.BLACK;
            ans_for_match.get(i).line_color=Color.BLACK;
        }
        return;
    }
    
    //The buttons for the Matching Quiz
    private static class Matchable_JButton extends JButton{
        
        protected Matchable_JButton matched_with;
        protected Matchable_JButton correct_match;
        
        protected Color line_color=Color.BLACK;
        
        protected boolean question;
        
        public Matchable_JButton(boolean question){
            super();
            this.question=question;
        }
        
        
        public Matchable_JButton(boolean question, String text){
            super(text);
            this.question=question;
        }
    }
    
    //The drawing that connects answers in the Matching quiz
    private class Matchable_Picture extends JPanel{
        
        public void paintComponent(Graphics graph){
            super.paintComponent(graph);
            for (int i=0; i<21; i++){
                if (questions_for_match.get(i).matched_with!=null){
                    graph.setColor(questions_for_match.get(i).line_color);
                    graph.drawLine(0, 
                    questions_for_match.get(i).getSize().height/2+questions_for_match.get(i).getLocation().y,
                    getSize().width,
                    questions_for_match.get(i).matched_with.getSize().height/2+questions_for_match.get(i).matched_with.getLocation().y);
                }
            }
        }
    }
    
    //The listener for the buttons of the Matching Quiz
    private class MatchButtonListener implements ActionListener{
        
        public void actionPerformed(ActionEvent event){
            
            if (submit_matches.getText()=="Continue"){
                return;
            }
            
            Matchable_JButton source=(Matchable_JButton)event.getSource();
            
            if (source.matched_with!=null){
                source.matched_with.matched_with=null;
                source.matched_with=null;
            }
            
            if (c_clicked_match==null){
                c_clicked_match=source;
                source.setBackground(Color.WHITE);
                source.setForeground(Color.BLUE);
            }
            
            else if (c_clicked_match==source){
                c_clicked_match=null;
                source.setBackground(null);
                source.setForeground(null);
            }
            
            else if (c_clicked_match.question==source.question){
                c_clicked_match.setBackground(null);
                c_clicked_match.setForeground(null);
                
                c_clicked_match=source;
                source.setBackground(Color.WHITE);
                source.setForeground(Color.BLUE);
            }
            
            else if (c_clicked_match.question!=source.question){
                
                c_clicked_match.matched_with=source;
                source.matched_with=c_clicked_match;
                
                c_clicked_match.setBackground(null);
                c_clicked_match.setForeground(null);
                c_clicked_match=null;
            }
            
            match_center_panel.repaint();
            return;
        }
    }
}
