package com.ltx.routefinder.acl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

public class FileHelperTest {

    private static Consumer<List<String>> printConsumer = System.out::println;

    @Spy
    @SuppressWarnings("unchecked")
    private Consumer<List<String>> consumerSpy = (Consumer<List<String>>) Mockito.spy(Consumer.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDoesNothingForEmptyFile() throws Exception {
        FileHelper.readFileLineByLine(getPath("empty_file.txt"), pair -> consumerSpy.accept(any()));
        Mockito.verify(consumerSpy, never()).accept(any());
    }

    @Test
    public void testThrowsExceptionForMissingFile() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Error while reading file");
        FileHelper.readFileLineByLine(getPath("missing_file.txt"), printConsumer);
    }

    @Test
    public void testThrowsExceptionForIncorrectFile() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Error while parsing file, incorrect pair of cities: [New Orleans Oklahoma City]");
        FileHelper.readFileLineByLine(getPath("incorrect_file.txt"), printConsumer);
    }

    @Test
    public void testDoesProcessingForCorrectFile() throws Exception {
        List<List<String>> verificationList = new ArrayList<>();
        FileHelper.readFileLineByLine(getPath("correct_file.txt"), verificationList::add);
        assertEquals(29, verificationList.size());
    }

    private String getPath(String fileName) {
        return "src" + File.separator + "test" + File.separator + "resources" + File.separator + fileName;
    }
}