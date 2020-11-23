package unicesumar.quartoBimestre;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Application extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JobQueue jobs = new JobQueue();
	
    private List<JobConsumer> consumers = new ArrayList<>();
    
    private List<JobProducer> producers = new ArrayList<>();
    
    public static void main(String[] args) {        
    	Application app = new Application();
        app.setSize(450, 250);
        app.setVisible(true);
    }

    public Application() {
        super();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.add(createInterface());
    }

    private JPanel createInterface() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());

        final JPanel firstRowPanel = new JPanel();
        firstRowPanel.setPreferredSize(new Dimension(600,40));
        firstRowPanel.setLayout(new BoxLayout(firstRowPanel, BoxLayout.X_AXIS));
        firstRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);        
        firstRowPanel.add(new JLabel("Total: "));

        final JTextField fieldProducerCount = new JTextField(20);
        final JButton btnAddProducer = new JButton("Adicionar Atividade");

        btnAddProducer.addActionListener(e -> {
            JobProducer newProducer = new JobProducer(jobs);
            producers.add(newProducer);
            fieldProducerCount.setText(String.valueOf(producers.size()));
            newProducer.start();
        });

        fieldProducerCount.setEnabled(false);
        fieldProducerCount.setMaximumSize(fieldProducerCount.getPreferredSize());

        btnAddProducer.setMaximumSize(btnAddProducer.getPreferredSize());

        firstRowPanel.add(fieldProducerCount);
        firstRowPanel.add(btnAddProducer);
        firstRowPanel.add(Box.createHorizontalGlue());
        
        final JPanel secondRowPanel = new JPanel();
        secondRowPanel.setLayout(new BoxLayout(secondRowPanel, BoxLayout.X_AXIS));
        secondRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);        
        secondRowPanel.add(new JLabel("Consumers:   "));
        
        final JTextField fieldConsumerCount = new JTextField(20);
        final JButton btnAddConsumer = new JButton("Adicionar");
        final JButton btnRemoveConsumer = new JButton("Remover");

        btnAddConsumer.addActionListener(e -> {
            JobConsumer newConsumer = new JobConsumer(jobs);
            consumers.add(newConsumer);
            fieldConsumerCount.setText(String.valueOf(consumers.size()));
            newConsumer.start();
        });

        btnRemoveConsumer.addActionListener(e -> {
            if (!consumers.isEmpty()) {
                consumers.get(0).stopConsumer();
                consumers.remove(0);
                fieldConsumerCount.setText(String.valueOf(consumers.size()));
            }
        });

        fieldConsumerCount.setEnabled(false);
        fieldConsumerCount.setMaximumSize(fieldConsumerCount.getPreferredSize());
        btnAddConsumer.setMaximumSize(btnAddConsumer.getPreferredSize());
        secondRowPanel.add(fieldConsumerCount);
        secondRowPanel.add(btnAddConsumer);
        secondRowPanel.add(btnRemoveConsumer);
        secondRowPanel.add(Box.createHorizontalGlue());

        
        final JPanel thirdRowPanel = new JPanel();
        thirdRowPanel.setLayout(new BoxLayout(thirdRowPanel, BoxLayout.X_AXIS));
        thirdRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);        
        thirdRowPanel.add(new JLabel("Na fila:   "));
        final JTextField fieldJobQueueCount = new JTextField(20);
        fieldJobQueueCount.setEnabled(false);
        fieldJobQueueCount.setMaximumSize(fieldJobQueueCount.getPreferredSize());
        thirdRowPanel.add(fieldJobQueueCount);

        thirdRowPanel.add(Box.createHorizontalGlue());

        final JPanel fourthRowPanel = new JPanel();
        fourthRowPanel.setLayout(new BoxLayout(fourthRowPanel, BoxLayout.X_AXIS));
        fourthRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fourthRowPanel.add(new JLabel("Em processamento:   "));

        final JTextField fieldJobProcessingCount = new JTextField(20);
        fieldJobProcessingCount.setEnabled(false);
        fieldJobProcessingCount.setMaximumSize(fieldJobProcessingCount.getPreferredSize());
        fourthRowPanel.add(fieldJobProcessingCount);

        fourthRowPanel.add(Box.createHorizontalGlue());
        
        this.jobs.addJobQueueListener(jobCount -> {
            fieldJobQueueCount.setText(String.valueOf(jobCount));
        }, processingCount -> {fieldJobProcessingCount.setText(String.valueOf(processingCount));});


        panel.add(firstRowPanel);
        panel.add(secondRowPanel);
        panel.add(thirdRowPanel);
        panel.add(fourthRowPanel);
        return panel;
    }
}
