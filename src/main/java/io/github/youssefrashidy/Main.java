package io.github.youssefrashidy;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import io.github.youssefrashidy.treeshell.TreeShell;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        TreeShell shell = new TreeShell();
        shell.shell();
    }
}