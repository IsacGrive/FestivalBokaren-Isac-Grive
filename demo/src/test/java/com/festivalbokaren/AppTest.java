package com.festivalbokaren;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class AppTest {

    @Test
    public void testBookTicket() {

        String input = "2\nSebastian\nLarsson\n1980\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        List<Ticket> bookedTickets = new ArrayList<>();

        App.bookTicket(new Scanner(System.in), bookedTickets);

        Assert.assertEquals(1, bookedTickets.size());
        Ticket bookedTicket = bookedTickets.get(0);
        Assert.assertEquals("standardPlus", bookedTicket.getTicketType());
        Assert.assertEquals("Sebastian", bookedTicket.getFirstName());
        Assert.assertEquals("Larsson", bookedTicket.getLastName());
        Assert.assertEquals(1980, bookedTicket.getBirthYear());
    }

    @Test
    public void testShowBookedTickets() {

        List<Ticket> bookedTickets = new ArrayList<>();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        App.showBookedTickets(bookedTickets);

        String expectedOutput = "No tickets have been booked yet.\n";
        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testShowBookedTickets_Exist() {

        List<Ticket> bookedTickets = new ArrayList<>();
        bookedTickets.add(new Ticket("standard", "Lucas", "Svensson", 1990));
        bookedTickets.add(new Ticket("VIP", "Lisa", "Simpsson", 1985));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        App.showBookedTickets(bookedTickets);

        String expectedOutput = "Booked Tickets:\n" +
                "Lucas Svensson, Birth Year: 1990\n" +
                "Lisa Simpsson, Birth Year: 1985\n";
        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testShowTicketCount() {

        List<Ticket> bookedTickets = new ArrayList<>();
        bookedTickets.add(new Ticket("standard", "Jonas", "Nilsson", 1990));
        bookedTickets.add(new Ticket("VIP", "Miley", "Smith", 1985));
        bookedTickets.add(new Ticket("standard", "Riley", "Johnson", 1995));
        bookedTickets.add(new Ticket("standardPlus", "Bob", "Miller", 1992));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        App.showTicketCount(bookedTickets);

        String expectedOutput = "Ticket Counts:\n" +
                "standard: 2\n" +
                "standardPlus: 1\n" +
                "VIP: 1\n" +
                "Fuktioner: 0\n";
        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testSaveAndLoadBookedTickets() throws IOException {

        List<Ticket> bookedTickets = new ArrayList<>();
        bookedTickets.add(new Ticket("standard", "John", "Paulsson", 1990));
        bookedTickets.add(new Ticket("VIP", "Beth", "Grimes", 1985));
        String fileName = "test_booked_tickets.csv";
        App.saveBookedTickets(bookedTickets);

        List<Ticket> loadedTickets = App.loadBookedTickets();

        Assert.assertEquals(bookedTickets.size(), loadedTickets.size());
        for (int i = 0; i < bookedTickets.size(); i++) {
            Ticket expectedTicket = bookedTickets.get(i);
            Ticket loadedTicket = loadedTickets.get(i);
            Assert.assertEquals(expectedTicket.getTicketType(), loadedTicket.getTicketType());
            Assert.assertEquals(expectedTicket.getFirstName(), loadedTicket.getFirstName());
            Assert.assertEquals(expectedTicket.getLastName(), loadedTicket.getLastName());
            Assert.assertEquals(expectedTicket.getBirthYear(), loadedTicket.getBirthYear());
        }

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
