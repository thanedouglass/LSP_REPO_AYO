#Assignment 3: AI Prompts and Transcript
This document contains a summary of the prompts and excerpts from the generative AI assistant's responses used for Assignment 3.

Initial Prompt:
"You are a world-class object-oriented Java programmer with expertise in object-oriented decomposition. Redesign the program attached into multiple classes with clearer responsibilities. Include Javadocs for each class and all public methods. The revised script must still comply with all the original requirements."

Inital AI Response:
"It looks like the provided Java code for the ETL process is a single monolithic class. I can definitely help you redesign this program using a more object-oriented approach. I've broken down the original script into several classes, each with a clear, single responsibility, to make the code more modular, maintainable, and readable. This refactoring adheres to the Single Responsibility Principle, making it much easier to test and modify each part independently. Here are the redesigned Java files with Javadoc comments for each class and public method."

Follow Up Prompt:
"I added the above 5 Java files under my assignment3 package, does the new framework comply with the following requirements - In Assignment 2, you built an ETL pipeline in Java. In this assignment, you will redesign your solution to be more object-oriented. The goal is to practice object-oriented decomposition, not perfection. You must also use a generative AI assistant to help with the redesign and explain how you used or adapted its suggestions."

AI Response:
"That's an excellent plan for your Assignment 3. The new framework I provided is a great starting point for this redesign and aligns perfectly with the requirements you've outlined. To answer your question directly: yes, this new framework complies with all the requirements for Assignment 3. It's a significant improvement over the single-class implementation from Assignment 2."

####Debugging and Troubleshooting
This section summarizes the additional work required to resolve compilation and version control issues. After generating the initial code, the AI assistant provided guidance to debug two key problems:
Prompt:
"i just commited my changed with the new java and reflections files then when i went to sync it gave me this output - * branch            main       -> FETCH_HEAD
   79b275d..687529f  main       -> origin/main
hint: You have divergent branches and need to specify how to reconcile them.
hint: You can do so by running one of the following commands sometime before
hint: your next pull:
hint:
hint:   git config pull.rebase false  # merge
hint:   git config pull.rebase true   # rebase
hint:   git config pull.ff only       # fast-forward only"

Prompt: Error: Could not find or load main class org.howard.edu.lsp.assignment3.ETLPipeline
Caused by: java.lang.NoClassDefFoundError: org/howard/edu/lsp/assignment3/ETLPipeline (wrong name: org/howard/edu/lsp/assignment2/ETLPipeline)

*Java Package Error: The program failed to run due to a NoClassDefFoundError. The AI assistant accurately identified the problem as a mismatch between the file's directory path (assignment3) and its internal package declaration (assignment2). The assistant provided the corrected code with the proper package name, which resolved the issue.
*Git Syncing Error: The git pull command failed, stating that the local and remote branches had "diverged." The assistant explained this concept and provided the precise command (git pull --no-rebase) needed to merge the divergent branches and successfully sync the repository.
