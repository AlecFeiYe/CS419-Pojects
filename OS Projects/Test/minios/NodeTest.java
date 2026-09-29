package minios;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    @Test
    public void NodeTestStartAt0() {
        Node node = new Node(0,100);
        assertEquals(0,node.getRelocation());
        assertEquals(100,node.getLimit());
        assertEquals(100,node.getNodeEndPoint());
    }
    @Test
    public void NodeTestStartAt10() {
        Node node = new Node(10,100);
        assertEquals(10,node.getRelocation());
        assertEquals(100,node.getLimit());
        assertEquals(110,node.getNodeEndPoint());
    }
}