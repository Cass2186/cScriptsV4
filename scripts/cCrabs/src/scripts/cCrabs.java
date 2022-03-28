package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.analysis.function.Exp;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.painting.template.basic.*;
import scripts.Data.Const;
import scripts.Data.Paint;
import scripts.Data.Vars;
import scripts.Gear.ProgressiveMeleeGear;
import scripts.Tasks.*;
import scripts.Tasks.Bank;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cCrabs v2", authors = {"Cass2186"}, category = "Combat")
public class cCrabs extends Script implements Starting, Ending, Painting, MessageListening07, Breaking, Arguments {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";


    Skills.SKILLS currentSkill = Skills.SKILLS.STRENGTH;

    public void populateInitialMap() {
        Log.log("[Debug]: Populating intial skills xp HashMap");
        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            Vars.get().skillStartXpMap.put(s, s.getXP());
        }
    }

    public HashMap<Skills.SKILLS, Integer> getXpMap() {
        if (Vars.get().skillStartXpMap == null || Vars.get().skillStartXpMap.size() == 0)
            populateInitialMap();

        HashMap<Skills.SKILLS, Integer> map = new HashMap<>();
        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            int startXp = Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp) {
                map.put(s, s.getXP() - startXp);
            }
        }

        return map;
    }

    @Override
    public void run() {
        Options.AttackOption.setNpcAttackOption(Options.AttackOption.LEFT_CLICK_WHERE_AVAILABLE);
        populateInitialMap();
        TaskSet tasks;
        tasks = new TaskSet(
                new Bank(),
                new EatDrink(),
                new MoveToCrabTile(),
                new HopWorlds(),
                new Fight(),
                new ResetAggro()
        );

        Utils.setCameraZoomAboveDefault();
        super.setLoginBotState(false);

        isRunning.set(true);
        addPaint();
        ExpHandler.updateStartXpAndLevel();

        Vars.get().startTime = System.currentTimeMillis();

        ProgressiveMeleeGear.getBestUsableGearList();

        while (isRunning.get()) {
            General.sleep(50, 150);
            if (Game.isInInstance()) //died
                break;

            if (!WorldHopper.isInMembersWorld())
                break;

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }

        }

    }


    @Override
    public void onStart() {
        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_H0C2eULZfbWeoF ", "acb35610-d868-4ce8-8797-0d2e659f87f4");
            }
        });
    }

    public void addPaint() {
        PaintTextRow template = PaintTextRow.builder().background(Color.DARK_GRAY.darker()).build();

        Collection<PaintRow> rows = new ArrayList<>();


        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Task").value(() -> status).build())
                .row(template.toBuilder().label("Force Rest Aggression")
                        .onClick(() -> Vars.get().shouldResetAggro = true )
                        .onClick(()->Log.debug("Force reset aggro clicked")).build())
                .row(template.toBuilder().condition(() -> Skill.RANGED.getXp() > Const.startRangeXp)
                        .label("Ranged")
                        .value(() -> PaintUtil.formatSkillString(Skill.RANGED,Const.startRangeLvl,
                                (Skill.RANGED.getXp() - Const.startRangeXp),
                                PaintUtil.getXpHr(Skill.RANGED, Vars.get().startRangeXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.STRENGTH.getXp() > Const.startStrXp)
                        .label("Strength")
                        .value(() -> PaintUtil.formatSkillString(Skill.STRENGTH, Const.startStrLvl,
                                (Skill.STRENGTH.getXp() - Const.startStrXp),
                                PaintUtil.getXpHr(Skill.STRENGTH, Vars.get().startStrXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.ATTACK.getXp() > Const.startAttXp)
                        .label("Attack")
                        .value(() -> PaintUtil.formatSkillString(Skill.ATTACK,Const.startAttLvl,
                                (Skill.ATTACK.getXp() - Const.startAttXp),
                                PaintUtil.getXpHr(Skill.ATTACK, Vars.get().startAttXp, Vars.get().startTime)))
                        .build())


                .row(template.toBuilder().condition(() -> Skill.DEFENCE.getXp() > Const.startDefXp)
                        .label("Defence")
                        .value(() -> PaintUtil.formatSkillString(Skill.DEFENCE, Const.startDefLvl,
                                (Skill.DEFENCE.getXp() - Const.startDefXp),
                                PaintUtil.getXpHr(Skill.DEFENCE, Vars.get().startDefXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.MAGIC.getXp() > Const.startMageXp)
                        .label("Magic")
                        .value(() -> PaintUtil.formatSkillString(Skill.MAGIC,Const.startMageLvl,
                                (Skill.MAGIC.getXp() - Const.startMageXp),
                                PaintUtil.getXpHr(Skill.MAGIC, Vars.get().startMageXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.HITPOINTS.getXp() > Const.startHPXP)
                        .label("Hitpoints")
                        .value(() -> PaintUtil.formatSkillString(Skill.HITPOINTS,Const.startHPLvl,
                                (Skill.HITPOINTS.getXp() - Const.startHPXP),
                                PaintUtil.getXpHr(Skill.HITPOINTS, Vars.get().startHPXP, Vars.get().startTime)))
                        .build())

                .rows(rows)
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        org.tribot.script.sdk.painting.Painting.addPaint(p -> paint.render(p));
    }

    @Override
    public void onPaint(Graphics g) {

        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);

        List<String> myString = new ArrayList<>(Arrays.asList(
                "cCrabs v2",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status
        ));
        if (xpMap != null) {
            for (Skills.SKILLS s : xpMap.keySet()) {
                int startLvl = Skills.getLevelByXP(Vars.get().skillStartXpMap.get(s));
                int xpHr = (int) (xpMap.get(s) / timeRanMin);
                long xpToLevel1 = Skills.getXPToLevel(s, s.getActualLevel() + 1);
                long ttl = (long) (xpToLevel1 / ((xpMap.get(s) / timeRan)));
                int gained = s.getActualLevel() - startLvl;
                if (gained > 0) {
                    myString.add(StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                            + " [" + s.getActualLevel() + " (+" + gained + ")]: "
                            + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                            "|| TNL: "
                            + Timing.msToString(ttl)
                    );
                } else {
                    myString.add(StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                            + " [" + s.getActualLevel() + "]: "
                            + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                            "|| TNL: "
                            + Timing.msToString(ttl)
                    );
                }
            }
        }
        // Create the template

        //  PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }

    @Override
    public void passArguments(HashMap<String, String> hashMap) {
        String scriptSelect = hashMap.get("custom_input");
        String clientStarter = hashMap.get("autostart");
        String input = clientStarter != null ? clientStarter : scriptSelect;
        Log.debug("[Args]: Argument entered: " + input);

        for (String arg : input.split(";")) {
            try {
                if (arg.toLowerCase().contains("pray")) {
                    Log.info("[Args] Praying enabled");
                    Vars.get().usingPrayer = true;
                }
                if (arg.toLowerCase().contains("progressive")){
                    Log.info("[Args] Progressive enabled");
                    Vars.get().progressiveMelee = true;
                    Vars.get().progressiveMeleeMap.put(Skill.ATTACK, 60);
                    Vars.get().progressiveMeleeMap.put(Skill.STRENGTH, 60);
                    Vars.get().progressiveMeleeMap.put(Skill.DEFENCE, 60);
                }


            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }

    @Override
    public void onEnd() {
        General.println("[Ending]: Runtime: " + Timing.msToString(this.getRunningTime()));
    }


    @Override
    public void onBreakEnd() {

    }

    @Override
    public void onBreakStart(long l) {
        PathingUtil.localNavigation(new RSTile(1760, 3420, 0));
    }


    @Override
    public void serverMessageReceived(String message) {
        if (message.contains("quiver")) {
            General.println("[Debug]: Out of ammo, ending script");
            isRunning.set(false);
        }
    }
}
