package scripts.Tasks.Runecrafting.Guardians;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GuardiansOfTheRiftHelperPlugin {

    private static final int MINIGAME_MAIN_REGION = 14484;

    private static final Set<Integer> GUARDIAN_IDS = ImmutableSet.of(43705, 43701, 43710, 43702, 43703, 43711, 43704, 43708, 43712, 43707, 43706, 43709, 43702);
    private static final Set<Integer> TALISMAN_IDS = GuardianInfo.ALL.stream().mapToInt(x -> x.talismanId).boxed().collect(Collectors.toSet());
    private static final int GREAT_GUARDIAN_ID = 11403;

    private static final int CATALYTIC_GUARDIAN_STONE_ID = 26880;
    private static final int ELEMENTAL_GUARDIAN_STONE_ID = 26881;
    private static final int POLYELEMENTAL_GUARDIAN_STONE_ID = 26941;

    private static final int ELEMENTAL_ESSENCE_PILE_ID = 43722;
    private static final int CATALYTIC_ESSENCE_PILE_ID = 43723;

    private static final int UNCHARGED_CELL_ITEM_ID = 26882;
    private static final int UNCHARGED_CELL_GAMEOBJECT_ID = 43732;
    private static final int CHISEL_ID = 1755;
    private static final int OVERCHARGED_CELL_ID = 26886;

    private static final int GUARDIAN_ACTIVE_ANIM = 9363;

    private static final int PARENT_WIDGET_ID = 48889857;
    private static final int CATALYTIC_RUNE_WIDGET_ID = 48889876;
    private static final int ELEMENTAL_RUNE_WIDGET_ID = 48889879;
    private static final int GUARDIAN_COUNT_WIDGET_ID = 48889886;
    private static final int PORTAL_WIDGET_ID = 48889883;

    private static final int PORTAL_ID = 43729;

    private static final String REWARD_POINT_REGEX = "Total elemental energy:[^>]+>(\\d+).*Total catalytic energy:[^>]+>(\\d+).";
    private static final Pattern REWARD_POINT_PATTERN = Pattern.compile(REWARD_POINT_REGEX);
    private static final String CHECK_POINT_REGEX = "You have (\\d+) catalytic points? and (\\d+) elemental points?";
    private static final Pattern CHECK_POINT_PATTERN = Pattern.compile(CHECK_POINT_REGEX);

    private static final int DIALOG_WIDGET_GROUP = 229;
    private static final int DIALOG_WIDGET_MESSAGE = 1;
    private static final String BARRIER_DIALOG_FINISHING_UP = "It looks like the adventurers within are just finishing up. You must<br>wait until they are done to join.";


    @Getter(AccessLevel.PACKAGE)
    private final Set<GameObject> guardians = new HashSet<>();
    @Getter(AccessLevel.PACKAGE)
    private final Set<GameObject> activeGuardians = new HashSet<>();
    @Getter(AccessLevel.PACKAGE)
    private final Set<Integer> inventoryTalismans = new HashSet<>();
    @Getter(AccessLevel.PACKAGE)
    private Npc greatGuardian;
    @Getter(AccessLevel.PACKAGE)
    private GameObject unchargedCellTable;
    @Getter(AccessLevel.PACKAGE)
    private GameObject catalyticEssencePile;
    @Getter(AccessLevel.PACKAGE)
    private GameObject elementalEssencePile;
    @Getter(AccessLevel.PACKAGE)
    private GameObject portal;

    @Getter(AccessLevel.PACKAGE)
    private boolean isInMinigame;
    @Getter(AccessLevel.PACKAGE)
    private boolean isInMainRegion;
    @Getter(AccessLevel.PACKAGE)
    private boolean outlineGreatGuardian = false;
    @Getter(AccessLevel.PACKAGE)
    private boolean outlineUnchargedCellTable = false;
    @Getter(AccessLevel.PACKAGE)
    private boolean shouldMakeGuardian = false;
    @Getter(AccessLevel.PACKAGE)
    private boolean isFirstPortal = false;

    @Getter(AccessLevel.PACKAGE)
    private int elementalRewardPoints;
    @Getter(AccessLevel.PACKAGE)
    private int catalyticRewardPoints;
    @Getter(AccessLevel.PACKAGE)
    private int currentElementalRewardPoints;
    @Getter(AccessLevel.PACKAGE)
    private int currentCatalyticRewardPoints;

    @Getter(AccessLevel.PACKAGE)
    private Optional<Instant> portalSpawnTime = Optional.empty();
    @Getter(AccessLevel.PACKAGE)
    private Optional<Instant> lastPortalDespawnTime = Optional.empty();
    @Getter(AccessLevel.PACKAGE)
    private Optional<Instant> nextGameStart = Optional.empty();
    @Getter(AccessLevel.PACKAGE)
    private int lastRewardUsage;


    private String portalLocation;
    private int lastElementalRuneSprite;
    private int lastCatalyticRuneSprite;
    private boolean areGuardiansNeeded = false;
    private int entryBarrierClickCooldown = 0;

    private boolean checkInMinigame() {
        GameState.State gameState = GameState.getState();
        if (gameState != GameState.State.LOGGED_IN
                && gameState != GameState.State.LOADING) {
            return false;
        }

        Optional<Widget> elementalRuneWidget = Widgets.get(PARENT_WIDGET_ID);
        return elementalRuneWidget.isPresent();
    }



}
