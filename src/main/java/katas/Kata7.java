package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Bookmark;
import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve the id, title, and smallest box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": "url)
*/
public class Kata7 {
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
                                        "boxart",
                                                movie.getBoxarts().stream()
                                                        .filter(boxArt -> smallestBoxArt.contains(boxArt.getWidth() * boxArt.getHeight()))
                                                        .collect(Collectors.toList()).get(0)
                                )
                                )
                )
                .collect(Collectors.toList());
    }
}
