package katas;

import model.Movie;
import util.DataUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Goal: Retrieve the url of the largest boxart using map() and reduce()
    DataSource: DataUtil.getMovieLists()
    Output: String
*/
public class Kata6 {
    public static String execute() {
        List<Movie> movies = DataUtil.getMovies();

        Integer largestBoxArt = movies.stream()
                .map(movie -> movie.getBoxarts())
                .map(boxArts -> boxArts.stream()
                        .map(boxArt -> boxArt.getHeight() * boxArt.getWidth())
                        .reduce(0,(largest, size) -> size > largest? size: largest)).max(Integer::compare).get();

        return movies.stream()
                .map(movie -> movie.getBoxarts())
                .map(boxArts -> boxArts.stream()
                        .filter(boxArt -> (boxArt.getWidth() * boxArt.getHeight() == largestBoxArt))
                        .map(boxArt -> boxArt.getUrl()).collect(Collectors.toList()))
                .filter(url -> !url.isEmpty())
                .collect(Collectors.toList()).get(0).get(0);
    }
}
