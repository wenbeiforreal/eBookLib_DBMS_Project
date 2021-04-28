### Proposition
This project is focusing on an eBook Library -- an online platform that breaks the restrictions of the library on simultaneous use and provides online lending services of a wide variety of digital books for readers, especially for the library users who are unhappy with all the time and economic cost of physically presenting at the library to borrow or return the print books.


### Plan
* Users can retrieve information about books
* Users can give feedback on books via ratings and comments
* Advanced functionality : Personalized search system where users can borrow books and selectively perform searches

### Technologies Used
mySQL, JDBC, JSP, XML, Clover ETL, Google Sheets, Excel Power Query 

### Database Used
* [Kaggle data source](https://www.kaggle.com/bahramjannesarr/goodreads-book-datasets-10m)
* There are 18 original attributes in the source file, including basic information for a book, such as Book name, ISBN, author, publishers.
* The data was imported into Author, Publisher and Book tables in our database, which contained around **100k rows**.

### UML
[![e-Book-UML.jpg](https://i.postimg.cc/VLwB845c/e-Book-UML.jpg)](https://postimg.cc/kDjtNQxj)

### Data Warehousing
* [New York Times Best Sellers - Kaggle](https://www.kaggle.com/cmenca/new-york-times-hardcover-fiction-best-sellers)
  * Contains information about hardcover fiction bestsellers over a period of 10 years
  * Collected data includes the book title, author, the date of the best seller list, the published date of the list, the book description, the rank (this week and last week), the publisher, number of weeks on the list, and the price
* [Goodreads Books - Kaggle](https://www.kaggle.com/jealousleopard/goodreadsbooks)
  * Contains information about books from the Goodreads API, ISBNs for each book, and the average rating for each book 
  * ISBN is the primary key from the query table, which makes correlation between the two tables easier


