package rga.contact.util;

import com.amulyakhare.textdrawable.TextDrawable;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;

public class UIHelperTest extends TestCase {

    @Test
    public void testGetTextDrawable() throws Exception {
        String name = "Jefferson     Almeida ";
        TextDrawable textDrawable = UIHelper.getTextDrawable(name);
        assertNotNull(textDrawable);
    }

    @Test
    public void testGetInitialLetters() throws Exception {
        String initialLetters = UIHelper.getInitialLetters(" Jefferson Almeida");
        assertEquals("JA", initialLetters);
    }

    @Test
    public void testGetInitialLetters2() throws Exception {
        String initialLetters = UIHelper.getInitialLetters(" Jefferson Almeida    ");
        assertEquals("JA", initialLetters);
    }


    @Test
    public void testGetInitialLetters3() throws Exception {
        String initialLetters = UIHelper.getInitialLetters("Maria Pereira da Silva    ");
        assertEquals("MP", initialLetters);
    }


    @Test
    public void testGetInitialLetters4() throws Exception {
        String initialLetters = UIHelper.getInitialLetters("J");
        assertEquals("J", initialLetters);
    }

    @Test
    public void testGetInitialLetters5() throws Exception {
        String initialLetters = UIHelper.getInitialLetters("");
        assertEquals("", initialLetters);
    }


    @Test
    public void testGetInitialLetters6() throws Exception {
        String name = "   Jefferson     Almeida     ";
        String initialLetters = UIHelper.getInitialLetters(name);
        assertEquals("JA", initialLetters);
    }

    @Test
    public void testGetInitialLetters7() throws Exception {
        String name = "   %  Jefferson     'Almeida'     ";
        String initialLetters = UIHelper.getInitialLetters(name);
        assertEquals("%J", initialLetters);
    }

    @Test
    public void testGetInitialLetters8() throws Exception {
        String name = "   %  &Jefferson     'Almeida'     ";
        String initialLetters = UIHelper.getInitialLetters(name);
        assertEquals("%&", initialLetters);
    }

    @Test
    public void testGetSuitableName() throws Exception {
        String expected = "Maria Pereira da...";
        String name = "Maria Pereira da Silva    ";
        assertEquals(expected, UIHelper.getSuitableName(name));
    }
}