# Smart Calculator
Smart Calculator is an open-ended task issued by [Hyperskill](https://hyperskill.org), slightly modified from the original problem statement.
The Smart Calculator is a command-line tool for performing simple arithmetic. It adheres to BODMAS rules and can even deal with large numbers, such as those beyond the range of
  integer or long data types.

The Smart Calculator uses a conversion method to perform an Infix-to-Postfix conversion. For more on how this works you can visit
  [CS2121 - Infix, Postfix and Prefix](http://www.cs.man.ac.uk/~pjj/cs212/fix.html). The program places each value/operator on a stack, and then calculates them
in-order to maintain BODMAS rules.

## How to Use ##
Open your favourite Command Line application and navigate to the location of the smart-calculator.jar file.
Once there, run the following command:
java -jar smart-calculator.jar
If at any time you get stuck, you can type /help to get a short explanation of how to use the application.
You can store variables by typing the name you wish to use followed by an = sign and the value you wish to assign:
>n = 10
>
The variable can be used in place of a number when performing arithmetic, e.g.
>n + 5
>
You can perform basic arithmetic, such as addition, subtraction, multiplication and division. You can use exponents to calculate values.
You can chain operations together as well, and BODMAS will be used to calculate the end value. Here we use brackets to give priority to 7^2:
>5 - 2 * 3 + (7 ^ 2)
>48

The Smart Calculator allows for huge numbers, even if they or their results are larger than standard int or long data types. Here we store the max value of long, and
  calculate it * 2:
> bignumber = 9223372036854775807

> Variable 'bignumber' with value '9223372036854775807' stored in memory.

> bignumber * 2

> 18446744073709551614
