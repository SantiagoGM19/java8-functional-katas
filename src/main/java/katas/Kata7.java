package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Bookmark;
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

        /*System.out.println(movieLists.stream().map(movieList -> movieList.getVideos().stream().collect(Collectors.toList())).collect(Collectors.toList()));*/

        return movieLists.stream()
                .map(movieList -> Map.of(movieList.getName() , movieList.getVideos().stream()
                                .map(movie -> List.of(
                                        movie.getId(),
                                        movie.getTitle(),
                                        movie.getBoxarts().stream()
                                                .filter(boxArt -> smallestBoxArt.stream()
                                                        .filter(integer -> integer == boxArt.getWidth() * boxArt.getHeight())
                                                        .map(element -> true).collect(Collectors.toList()).get(0)
                                                )
                                                .map(boxArt -> boxArt.getUrl()).collect(Collectors.toList()).get(0)
                                )
                                )
                        )
                ).collect(Collectors.toList());

        /*return ImmutableList.of(ImmutableMap.of("id", 5, "title", "Bad Boys", "boxart", "url"));*/
    }
}
