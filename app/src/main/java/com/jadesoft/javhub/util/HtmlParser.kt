package com.jadesoft.javhub.util

import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.data.model.Link
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.model.MovieDetail
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object HtmlParser {
    fun parserMovies(html: String, censored: Boolean): List<Movie> {
        val document: Document = Jsoup.parse(html)

        val movies = mutableListOf<Movie>()

        val elements = document.selectXpath("//a[@class='movie-box']")
        for (ele in elements) {
            val code = ele.selectXpath(".//date[1]").text()
            val title = ele.selectXpath(".//img").attr("title")
            val coverOrigin = ele.selectXpath(".//img").attr("src")
            val cover: String = if (coverOrigin.startsWith("https")) {
                coverOrigin
            } else {
                "https://www.javbus.com$coverOrigin"
            }

            movies.add(
                Movie(
                    code = code,
                    title = title,
                    cover = cover,
                    censored = censored
                )
            )
        }
        return movies
    }

    fun parseActresses(censored: Boolean, html: String): List<Actress> {
        val document: Document = Jsoup.parse(html)

        val actresses = mutableListOf<Actress>()

        val elements = document.selectXpath("//div[@class='item']/a")
        for (ele in elements) {
            val code = ele.attr("href").substringAfterLast("/")
            val name = ele.selectXpath(".//img").attr("title")
            val coverOrigin = ele.selectXpath(".//img").attr("src")
            val cover: String = if (coverOrigin.startsWith("https")) {
                coverOrigin
            } else {
                "https://www.javbus.com$coverOrigin"
            }
            actresses.add(
                Actress(
                    code = code,
                    name = name,
                    avatar = cover,
                    censored = censored
                )
            )
        }
        return actresses
    }

    fun parseActressSearch(html: String): List<Actress> {
        val document: Document = Jsoup.parse(html)

        val actresses = mutableListOf<Actress>()

        val elements = document.selectXpath("//div[@id='waterfall']//a")
        for (ele in elements) {
            val code = ele.attr("href").substringAfterLast("/")
            val name = ele.selectXpath(".//span").text().substringBeforeLast(" ")
            val censored = ele.selectXpath(".//button").text() == "有碼"
            val coverOrigin = ele.selectXpath(".//img").attr("src")
            val cover: String = if (coverOrigin.startsWith("https")) {
                coverOrigin
            } else {
                "https://www.javbus.com$coverOrigin"
            }
            actresses.add(
                Actress(
                    code = code,
                    name = name,
                    avatar = cover,
                    censored = censored
                )
            )
        }
        return actresses
    }

    fun parseActressDetail(html: String, code: String, censored: Boolean): Actress {
        val document: Document = Jsoup.parse(html)

        val div = document.selectXpath("//div[contains(@class, 'avatar-box')]")
        val mainInfo = div[0].selectXpath(".//img")
        val detailInfo = div[0].selectXpath(".//div[@class='photo-info']/p")
        val name = mainInfo.attr("title")
        val avatar = "https://www.javbus.com${mainInfo.attr("src")}"
        var birthday = ""
        var age = ""
        var height = ""
        var cup = ""
        var bust = ""
        var hip = ""
        var waist = ""
        var birthplace = ""
        var hobby = ""
        for (p in detailInfo) {
            if ("生日" in p.text()) birthday = p.text().substringAfterLast(" ")
            if ("年齡" in p.text()) age = p.text().substringAfterLast(" ")
            if ("身高" in p.text()) height = p.text().substringAfterLast(" ")
            if ("罩杯" in p.text()) cup = p.text().substringAfterLast(" ")
            if ("胸圍" in p.text()) bust = p.text().substringAfterLast(" ")
            if ("腰圍" in p.text()) hip = p.text().substringAfterLast(" ")
            if ("臀圍" in p.text()) waist = p.text().substringAfterLast(" ")
            if ("出生地" in p.text()) birthplace = p.text().substringAfterLast(" ")
            if ("愛好" in p.text()) hobby = p.text().substringAfterLast(" ")
        }
        val actressDetail = Actress(
            code = code,
            censored = censored,
            name = name,
            avatar = avatar,
            birthday = birthday,
            age = age,
            height = height,
            cup = cup,
            bust = bust,
            hip = hip,
            waist = waist,
            birthplace = birthplace,
            hobby = hobby
        )
        return actressDetail
    }

    fun parseMovieDetail(html: String): MovieDetail {
        val document: Document = Jsoup.parse(html)

        val code = document.selectXpath("//div[@class='col-md-3 info']/p[1]/span[2]").text()

        val title = document.selectXpath("//div[@class='container']/h3")
            .text().substringAfter(" ", "").substringBeforeLast(" ")

        val bigCoverOrigin = document.selectXpath("//a[@class='bigImage']").attr("href")
        val bigCover: String = if (bigCoverOrigin.startsWith("https")) {
            bigCoverOrigin
        } else {
            "https://www.javbus.com$bigCoverOrigin"
        }

        val publishDate = document.selectXpath("//div[@class='col-md-3 info']/p[2]")
            .text().substringAfter(" ")

        val duration = document.selectXpath("//div[@class='col-md-3 info']/p[3]")
            .text().substringAfter(" ")

        val links = document.selectXpath("//div[@class='col-md-3 info']/p/a")
        var director = Link("", "")
        var producer = Link("", "")
        var publisher = Link("", "")
        var series = Link("", "")
        for (link in links) {
            if ("director" in link.attr("href")) {
                director = Link(link.attr("href").substringAfterLast("/"), link.text())
            }
            if ("studio" in link.attr("href")) {
                producer = Link(link.attr("href").substringAfterLast("/"), link.text())
            }
            if ("label" in link.attr("href")) {
                publisher = Link(link.attr("href").substringAfterLast("/"), link.text())
            }
            if ("series" in link.attr("href")) {
                series = Link(link.attr("href").substringAfterLast("/"), link.text())
            }
        }

        val genresElements = document.selectXpath("//span[@class='genre']/label/a")
        val genres = mutableListOf<Link>()
        for (ele in genresElements) {
            val genreCode = ele.attr("href").substringAfterLast("/")
            val genreName = ele.text()
            genres.add(Link(genreCode, genreName))
        }
        val censored = !genresElements[0].attr("href").contains("uncensored")

        val actressElements = document.selectXpath("//a[@class='avatar-box']")
        val actress = mutableListOf<Actress>()
        for (ele in actressElements) {
            val actressCode = ele.attr("href").substringAfterLast("/")
            val actressName = ele.selectXpath(".//span").text()
            val actressAvatarOrigin = ele.selectXpath(".//img").attr("src")
            val actressAvatar = if (actressAvatarOrigin.startsWith("https")) {
                actressAvatarOrigin
            } else {
                "https://www.javbus.com$actressAvatarOrigin"
            }
            actress.add(
                Actress(
                    code = actressCode,
                    name = actressName,
                    avatar = actressAvatar,
                    censored = censored
                )
            )
        }

        val imagesElements = document.selectXpath("//a[@class='sample-box']")
        val images = mutableListOf<String>()
        for (ele in imagesElements) {
            val imgOrigin = ele.attr("href")
            val img: String = if (imgOrigin.startsWith("https")) {
                imgOrigin
            } else {
                "https://www.javbus.com$imgOrigin"
            }
            images.add(img)
        }

        val similarMoviesElements = document.selectXpath("//div[@id='related-waterfall'][1]/a")
        val similarMovies = mutableListOf<Movie>()
        for (ele in similarMoviesElements) {
            val coverOrigin = ele.selectXpath(".//img").attr("src")
            val cover: String = if (coverOrigin.startsWith("https")) {
                coverOrigin
            } else {
                "https://www.javbus.com$coverOrigin"
            }
            similarMovies.add(
                Movie(
                    code = ele.attr("href").substringAfterLast("/"),
                    title = ele.attr("title"),
                    cover = cover,
                    censored = censored
                )
            )
        }

        return MovieDetail(
            code = code,
            censored = censored,
            title = title,
            bigCover = bigCover,
            publishDate = publishDate,
            duration = duration,
            director = director,
            publisher = publisher,
            producer = producer,
            series = series,
            genres = genres,
            actress = actress,
            images = images,
            similarMovies = similarMovies
        )
    }

    fun parseGenres(html: String): Map<String, List<Genre>> {
        val document: Document = Jsoup.parse(html)

        val genres = mutableListOf<Genre>()

        val h4s = document.selectXpath("//h4").dropLast(0)
        val mainGenres = mutableListOf<String>()
        h4s.forEach{ item ->
            mainGenres.add(item.text())
        }
        val divs = document.selectXpath("//div[@class='row genre-box']")
        divs.forEachIndexed { index, div ->
            val subGenres = div.selectXpath(".//a")
            subGenres.forEach{ item ->
                val subGenre = item.text()
                val url = item.attr("href")
                genres.add(
                    Genre(
                        mainGenre = mainGenres[index],
                        subGenre = subGenre,
                        code = url.substringAfterLast("/"),
                        url = url
                    )
                )
            }
        }
        val groupedGenres = genres.groupBy { it.mainGenre }
        return groupedGenres
    }
}