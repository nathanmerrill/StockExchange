package com.ppcg.stockexchange;

import com.ppcg.kothcomm.game.GameManager;
import com.ppcg.kothcomm.game.PlayerType;
import com.ppcg.kothcomm.game.tournaments.TournamentRunner;
import com.ppcg.kothcomm.game.tournaments.types.EloTournament;
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
            if (args[0].equals("download")){
                download(fileManager);
            }
            if (args[0].equals("run")){
                run(fileManager);
            }
        } else {
            System.out.println("No command given, executing 'run' by default");
            run(fileManager);
        }
    }
    private static void download(SubmissionFileManager fileManager){
        Downloader downloader = new Downloader(fileManager, 0);
        downloader.downloadQuestions();
    }
    private static void run(SubmissionFileManager fileManager){
        FileReader reader = new FileReader(fileManager);
        List<PlayerType<Player>> players = reader.registerAllSubmissions(Player.class, PipeBot::new);
        GameManager<Player> manager = new GameManager<>(StockExchange::new);
        manager.register(players);
        manager.minPlayerCount(StockExchange.NUM_STOCKS);
        TournamentRunner<Player> runner = new TournamentRunner<>(new EloTournament<>(manager));
        System.out.println(runner.run(50));
    }
}
