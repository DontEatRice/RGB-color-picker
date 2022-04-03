package pac;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;



public class Frame extends JFrame {
    final Color DEFAULT_COLOR = new Color(127, 127, 127);

    private final JComponentContainer leftSlidersListeners;
    private final JComponentContainer rightSlidersListeners;

    public Frame() {
        leftSlidersListeners = new JComponentContainer();
        rightSlidersListeners = new JComponentContainer();

        setTitle("RGB color picker");
        // setSize(600, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        Rectangles rectangles = new Rectangles();

        getContentPane().add(rectangles);
        getContentPane().add(new Tools());
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));

        pack();

        ImageIcon icon = new ImageIcon("src\\img\\icon.png");

        setIconImage(icon.getImage());
    }

    class Rectangles extends JPanel {

        public Rectangles() {
            setPreferredSize(new Dimension(250, 500));
            setBorder(new EmptyBorder(25, 25, 25, 25));
            setLayout(new GridLayout(2, 1, 0, 50));


            JPanel topRect = new FilledPanel(DEFAULT_COLOR);
            topRect.setLayout(new GridLayout(0, 1));
            topRect.setBorder(new EmptyBorder(40, 40, 40, 40));

            JPanel smallerRect = new FilledPanel(DEFAULT_COLOR);
            rightSlidersListeners.addComponent(smallerRect);

            topRect.add(smallerRect);

            JPanel bottomRect = new FilledPanel(DEFAULT_COLOR);
            bottomRect.setLayout(new GridLayout(2, 2));

            JPanel filledPanel = new JPanel();
            filledPanel.setBackground(DEFAULT_COLOR);
            leftSlidersListeners.addComponent(filledPanel);

            bottomRect.add(filledPanel);
            bottomRect.add(new EmptyPanel());

            filledPanel = new JPanel();
            filledPanel.setBackground(DEFAULT_COLOR);

            leftSlidersListeners.addComponent(filledPanel);

            bottomRect.add(new EmptyPanel());
            bottomRect.add(filledPanel);


            leftSlidersListeners.addComponent(topRect);
            rightSlidersListeners.addComponent(bottomRect);

            add(topRect);
            add(bottomRect);
        }

    }

    class Tools extends JPanel {
        public Tools() {
            setPreferredSize(new Dimension(350, 500));
            setBorder(new EmptyBorder(25, 0, 25, 15));
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            JPanel sliders = new JPanel();
            sliders.setPreferredSize(new Dimension(335, 350));
            sliders.setLayout(new GridLayout(1, 2, 5, 0));

            for(int i = 1; i<=2; i++) {
                JPanel slidersSet = new JPanel();

                JTextArea textArea = new JTextArea("R: "+ DEFAULT_COLOR.getRed() +", G: " + DEFAULT_COLOR.getGreen() +", B: " + DEFAULT_COLOR.getBlue());
                textArea.setFont(new Font("Arial", Font.PLAIN, 15));
                textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
                textArea.setEditable(false);

                slidersSet.add(new SlidersPanel(i, textArea));
                slidersSet.add(textArea);

                sliders.add(slidersSet);
            }


            JPanel text = new FilledPanel(DEFAULT_COLOR);
            text.setPreferredSize(new Dimension(335, 100));
            text.setLayout(new GridLayout(1, 1));

            rightSlidersListeners.addComponent(text);

            JLabel textContent = new JLabel("@DontEatRice");
            textContent.setHorizontalAlignment(SwingConstants.CENTER);
            textContent.setFont(new Font("Arial", Font.BOLD, 18));

            leftSlidersListeners.addComponent(textContent);
            text.add(textContent);

            add(sliders);
            add(text);

        }

        class SlidersPanel extends JPanel {
            private final JTextArea textArea;
            private final Map<String, JSlider> sliders;

            SlidersPanel(int slidersID, JTextArea textArea) {
                this.textArea = textArea;
                setPreferredSize(new Dimension(165, 300));
                setLayout(new GridLayout(1, 3, 0, 5));
                setBorder(new EmptyBorder(0, 5, 0, 5));

                sliders = new LinkedHashMap<>();
                sliders.put("R", new JSlider(JSlider.VERTICAL, 0, 255, DEFAULT_COLOR.getRed()));
                sliders.put("G", new JSlider(JSlider.VERTICAL, 0, 255, DEFAULT_COLOR.getGreen()));
                sliders.put("B", new JSlider(JSlider.VERTICAL, 0, 255, DEFAULT_COLOR.getBlue()));

                sliders.values()
                        .forEach(item -> {
                            item.setPaintLabels(true);
                            item.setPaintTicks(true);
                            item.setMajorTickSpacing(85);
                            item.setMinorTickSpacing(17);
                            item.setFont(new Font("Arial", Font.PLAIN, 11));
                            add(item);

                            item.addChangeListener(e -> {
                                switch (slidersID) {
                                    case 1 -> leftSlidersListeners.setBackground(this.getCurrentColor());
                                    case 2 -> rightSlidersListeners.setBackground(this.getCurrentColor());
                                    default -> System.out.println("Wrong sliderID");
                                }
                                updateText();
                            });
                        });

            }

            private Color getCurrentColor() {
                return new Color(sliders.get("R").getValue(), sliders.get("G").getValue(), sliders.get("B").getValue());
            }

            public void updateText() {
                textArea.setText("R: " + sliders.get("R").getValue() + ", G: " + sliders.get("G").getValue() +
                        ", B: " + sliders.get("B").getValue());
            }
        }
    }

    class EmptyPanel extends JPanel {
        public EmptyPanel() {
            setBackground(new Color(0, 0, 0, 0));
        }
    }

    class FilledPanel extends JPanel {
        public FilledPanel(Color c) {
            setBackground(c);
        }
    }

}
