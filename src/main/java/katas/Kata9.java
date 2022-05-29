package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Bookmark;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve each video's id, title, middle interesting moment time, and smallest box art url
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl")
*/
public class Kata9 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        List<Integer> smallestBoxArt = movieLists.stream()
                .flatMap(movieList -> movieList.getVideos().stream()
                        .map(movie -> movie.getBoxarts().stream()
                                .map(boxArt -> boxArt.getWidth() * boxArt.getHeight())
                                .reduce(Integer::min).get()
                        )
                ).collect(Collectors.toList());

        return movieLists.stream()
                .flatMap(movieList -> movieList.getVideos().stream()
                        .map(movie -> ImmutableMap.of(
                                        "id",
                                        movie.getId(),
                                        "title",
                                        movie.getTitle(),
                                        "time",
                                        movie.getInterestingMoments().get((int) Math.round(Math.ceil(movie.getInterestingMoments().size()/2) - 1)).getTime(),
                                        "boxart",
                                        movie.getBoxarts().stream()
                                                .filter(boxArt -> smallestBoxArt.contains(boxArt.getWidth() * boxArt.getHeight()))
                                                .collect(Collectors.toList()).get(0).getUrl()
                                )
                        )
                )
                .collect(Collectors.toList());

        /*return ImmutableList.of(ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl"));*/
    }
}
