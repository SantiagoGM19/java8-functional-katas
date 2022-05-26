package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.BoxArt;
import model.MovieList;
import util.DataUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve id, title, and a 150x200 box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": BoxArt)
*/
public class Kata4 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();
        return movieLists
                .stream()
                .flatMap(movieList -> movieList.
                        getVideos()
                        .stream()
                        .map(video -> ImmutableMap.of(video.getId(), ImmutableList.of(video.getTitle(), video.getBoxarts()
                                .stream()
                                .filter(boxArt -> boxArt.getWidth() == 150 && boxArt.getHeight() == 200)
                                .map(boxArt -> ImmutableList.of(boxArt.getWidth(), boxArt.getHeight(), boxArt.getUrl()))
                                .collect(Collectors.toCollection(ArrayList::new))))))
                        .collect(Collectors.toCollection(ArrayList::new));
        /*return ImmutableList.of(ImmutableMap.of("id", 5, "title", "Bad Boys", "boxart", new BoxArt(150, 200, "url")));*/
    }
}
