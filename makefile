#makefile for assignment1

# Bonile Kunana

# 31/03/2021

JAVAC=/usr/bin/javac
.SUFFIXES: .javac .class
SRCDIR = src
BINDIR = bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES =BinaryTreeNode.class BTQueueNode.class BTQueue.class BinaryTree.class BinarySearchTree.class Student.class AccessArrayApp.class AccessBSTApp.class AutomationArray.class AutomationBST.class
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

run: $(CLASS_FILES)
	java -cp bin AccessArrayApp