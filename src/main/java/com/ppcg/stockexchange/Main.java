package com.ppcg.stockexchange;

import com.ppcg.kothcomm.game.GameManager;
import com.ppcg.kothcomm.game.PlayerType;
import com.ppcg.kothcomm.game.TournamentRunner;
import com.ppcg.kothcomm.game.scoreboards.EloScoreboard;
import com.ppcg.kothcomm.game.tournaments.AdjacentPlayerProvider;
import com.ppcg.kothcomm.game.tournaments.SimilarScoreProvider;
import com.ppcg.kothcomm.loader.Downloader;
import com.ppcg.kothcomm.loader.FileReader;
import com.ppcg.kothcomm.loader.SubmissionFileManager;

import java.io.File;
import java.util.List;

public class Main {
    public final static String defaultSubmissionDirectory = "./submissions";

    public static void main(String[] args){
        String directory = defaultSubmissionDirectory;
        if (args.length > 1){
            directory = args[1];
        }
        SubmissionFileManager fileManager = new SubmissionFileManager(new File(directory));
        if (args.length > 0){
            for (String arg: args) {
                if (arg.equals("download")) {
                    download(fileManager);
                }
                if (arg.equals("run")) {
                    run(fileManager);
                }
                if (arg.equals("runJava")) {
                    runJava(fileManager);
                }
                if (arg.equals("compile")) {
                    compile(fileManager);
                }
            }
        } else {
            System.out.println("No command given, executing 'run' by default");
            run(fileManager);
        }
    }
    private static void download(SubmissionFileManager fileManager){
        Downloader downloader = new Downloader(fileManager, 91566);
        downloader.downloadQuestions();
    }

    private static List<PlayerType<Player>> compile(SubmissionFileManager fileManager){
        FileReader reader = new FileReader(fileManager);
        return reader.registerAllSubmissions(Player.class, PipeBot::new);
    }

    private static void runJava(SubmissionFileManager fileManager){
        FileReader reader = new FileReader(fileManager);
        run(reader.registerJavaFiles(Player.class));
    }

    private static void run(List<PlayerType<Player>> players){
        GameManager<Player> manager = new GameManager<>(StockExchange::new);
        manager.register(players);
        TournamentRunner<Player> runner = new TournamentRunner<>(new SimilarScoreProvider<>(manager, 25), () -> new EloScoreboard<>());
        System.out.println(runner.run(50).scoreTable());
    }

    @SuppressWarnings("Convert2MethodRef")
    private static void run(SubmissionFileManager fileManager){
        run(compile(fileManager));
    }
}
