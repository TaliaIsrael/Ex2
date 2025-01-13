I'm Talia Israel, a first-year Computer Science student at Ariel University.
This is my second project, and it’s a leap forward
from basic operations to building a functional spreadsheet system – think of it as creating a lightweight Excel from scratch!
**What It Does**
This spreadsheet can handle:
•	Text and Numbers: Store plain text or numeric values in cells.
•	Formulas: Start with = and include basic math, like =1+2.
•	Cell References: Use values from other cells, like =A1+B2.
•	Complex Expressions: Support for parentheses, like =((1+2)*3)-4.
•	Error Detection: Identify invalid formulas, bad references, or circular dependencies.
**Key Components**
**Here’s how the system is built:**
•	SCell.java: Handles the logic for individual cells, like storing content and validating types.
•	Ex2Sheet.java: Manages the entire spreadsheet grid and its interactions.
•	FormTools.java: Processes and evaluates formulas.
•	CellEntry.java: Decodes cell references like A1 or B2 into coordinates.
Formula Features
The system supports:
java
Copy code
**// Examples of valid formulas:**
=1.5             // Direct number  
=A1              // Reference to another cell  
=2+3             // Basic arithmetic  
=(1+2)*3         // Parentheses and operator precedence  
=A1/B2           // Using values from other cells  

// Error Handling:
ERR_FORM         // Invalid formula format  
ERR_CYCLE        // Circular dependency detected  
ERR_REF          // Invalid cell reference  
Testing
**To ensure everything works perfectly, I thoroughly tested:**
1.	Formula Evaluation: Making sure expressions like =1+2 compute correctly.
2.	Cell References: Verifying that A1 can use values from B2.
3.	Error Detection: Catching invalid formulas or circular references.
4.	Data Types: Ensuring numbers and text are identified properly.
**What I Learned**
This project was an exciting opportunity to:
•	Dive deep into object-oriented programming in Java.
•	Handle complex data dependencies (like resolving cell references).
•	Improve error validation and debugging skills.
•	Appreciate the importance of rigorous testing for larger systems.

**Challenges Encountered**
The first part of the assignment was successfully completed, and all functions, including formula evaluation and cell type validation, worked as expected.
However, after moving on to the second part, which involved implementing the Sheet functionality (e.g., SCell, CellEntry, and the depth algorithm),
the functions from the first part stopped working correctly.
