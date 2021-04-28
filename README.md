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

### Analysis
* Correlation between the best sellers and number of pages
 * Almost half the best selling books are between 200-400 pages. This gives an insight of what kind of book lengths people prefer reading more. Longer books (>800 pages) rarely make it to best sellers, so we can definitely remove/reduce these books from the database. In order to attract more users to finish reading books and keep utilizing the platform, ideally, more books with pages between 100-500 should be uploaded.

[![Capture.jpg](https://i.postimg.cc/YCvsQxFZ/Capture.jpg)](https://postimg.cc/G41KCGqx)

* Correlation between the best sellers and the ratings
 * The maximum books in the best seller book collection have a rating between 3.5 and 4.5. There are only four books that have a rating between 4.5-5, and as a library, we can definitely strive to increase the count of those books. Only one book has a rating below 3.0, which gives an insight into the quality of books in the database. 

[![Capture.jpg](https://i.postimg.cc/nrHHLWC6/Capture.jpg)](https://postimg.cc/YGscnbN3)
