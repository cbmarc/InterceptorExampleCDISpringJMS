import org.junit.Test;
import org.junit.runner.RunWith;
import org.smallpawn.example.util.JmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@EnableJms
@DirtiesContext
public class CdiSpringTest {

    @Autowired
    private JmsUtil jmsUtil;


    @Test
    public void jmsSendTest() {
        jmsUtil.sendJMSMessage("DefaultQueue", "ping!");
    }
}
