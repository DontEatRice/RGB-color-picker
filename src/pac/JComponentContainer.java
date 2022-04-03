package pac;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JComponentContainer {

    private List<JComponent> container;

    public JComponentContainer() {
        this.container = new ArrayList<>();
    }


    public void addComponent(JComponent jc) {
        this.container.add(jc);
    }

    public void setBackground(Color c) {
        container.forEach(component -> {
            if (component.getClass() == JLabel.class) {
                component.setForeground(c);
                return;
            }
            component.setBackground(c);
        });
    }
}
