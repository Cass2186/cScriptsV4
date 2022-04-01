package scripts.QuestPackages.DepthsofDespair;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DepthsOfDespair implements QuestTask {

    int BOOK_ID = 21756;
    int LOBSTER = 379;
    int MIND_RUNE = 558;
    int AIR_RUNE = 556;
    int STAFF_OF_FIRE = 1387;

    RSArea START_AREA = new RSArea(new RSTile(1778, 3573, 0), new RSTile(1785, 3570, 0));
    RSArea LARGE_START_AREA = new RSArea(new RSTile(1785, 3565, 0), new RSTile(1773, 3578, 0));
    RSArea ARCEUS_LIBRARY = new RSArea(new RSTile(1638, 3802, 0), new RSTile(1626, 3813, 0));
    RSArea KITCHEN_AREA = new RSArea(new RSTile(1778, 3563, 0), new RSTile(1772, 3569, 0));
    RSArea BOTTOM_FLOOR_NE_BAY = new RSArea(new RSTile(1657, 3815, 0), new RSTile(1647, 3830, 0));
    RSArea CAVE_ENTRANCE = new RSArea(new RSTile(1646, 3446, 0), new RSTile(1642, 3450, 0));
    RSArea WC_GUILD_TELE_AREA = new RSArea(new RSTile(1658, 3509, 0), new RSTile(1667, 3499, 0));
    RSArea CAVE_DESTINATION = new RSArea(new RSTile(1699, 9750, 0), new RSTile(1689, 9759, 0));
    RSArea CAVE_BOTTOM_OF_ENTRANCE_LADDER = new RSArea(new RSTile(1649, 9843, 0), new RSTile(1644, 9849, 0));
    RSArea WHOLE_CAVE = new RSArea(new RSTile(1640, 9852, 0), new RSTile(1747, 9727, 0));
    RSArea PRE_SHORTCUT_1 = new RSArea(new RSTile(1713, 9824, 0), new RSTile(1709, 9825, 0));
    RSArea PRE_SHORTCUT_2 = new RSArea(new RSTile(1708, 9801, 0), new RSTile(1712, 9798, 0));
    RSArea CAVE_LADDER = new RSArea(new RSTile(1669, 9801, 0), new RSTile(1675, 9797, 0));
    RSArea CAVE_LADDER_LARGE = new RSArea(new RSTile(1668, 9804, 0), new RSTile(1680, 9795, 0));
    RSArea POST_STONES = new RSArea(new RSTile(1702, 9802, 0), new RSTile(1687, 9795, 0));

    RSArea MIDDLE_FLOOR_NW_BAY = new RSArea(new RSTile(1607, 3831, 1), new RSTile(1624, 3816, 1));
    RSArea MIDDLE_FLOOR_SW_BAY = new RSArea(new RSTile(1624, 3784, 1), new RSTile(1607, 3798, 1));

    RSArea TOP_FLOOR_NW_BAY = new RSArea(new RSTile(1623, 3817, 2), new RSTile(1608, 3830, 2));
    RSArea TOP_FLOOR_SW_BAY = new RSArea(new RSTile(1623, 3785, 2), new RSTile(1608, 3798, 2));
    RSArea TOP_FLOOR_CENTRE_BAY = new RSArea(new RSTile(1641, 3800, 2), new RSTile(1625, 3815, 2));
    RSArea TOP_FLOOR_NE_BAY = new RSArea(new RSTile(1640, 3831, 2), new RSTile(1658, 3816, 2));

    RSArea BOTTOM_NW_BAY = new RSArea(new RSTile(1625, 3815, 0), new RSTile(1607, 3830, 0));
    RSArea BOTTOM_SW_BAY = new RSArea(new RSTile(1625, 3785, 0), new RSTile(1608, 3800, 0));

    RSTile BOTTOM_SW_CORNER = new RSTile(1608, 3785, 0);


    RSArea MIDDLE_FLOOR_CENTRE_AREA = new RSArea(new RSTile(1641, 3799, 1), new RSTile(1625, 3815, 1));

    /**
     * BOOK TILES
     */
    RSTile[] MIDDLE_FLOOR_SW_BOOKSHELF_TILES = {new RSTile(1607, 3788, 1), new RSTile(1609, 3790, 1), new RSTile(1611, 3790, 1), new RSTile(1613, 3790, 1), new RSTile(1614, 3789, 1), new RSTile(1615, 3788, 1), new RSTile(1615, 3790, 1), new RSTile(1614, 3791, 1), new RSTile(1613, 3791, 1), new RSTile(1610, 3791, 1), new RSTile(1609, 3791, 1), new RSTile(1608, 3791, 1), new RSTile(1607, 3793, 1), new RSTile(1607, 3794, 1), new RSTile(1621, 3799, 1), new RSTile(1623, 3799, 1), new RSTile(1624, 3798, 1), new RSTile(1624, 3796, 1), new RSTile(1624, 3792, 1), new RSTile(1624, 3791, 1), new RSTile(1623, 3789, 1), new RSTile(1621, 3789, 1), new RSTile(1620, 3788, 1), new RSTile(1621, 3788, 1), new RSTile(1624, 3787, 1), new RSTile(1624, 3786, 1), new RSTile(1619, 3784, 1), new RSTile(1618, 3784, 1), new RSTile(1616, 3784, 1), new RSTile(1612, 3784, 1), new RSTile(1611, 3784, 1)};
    RSTile[] MIDDLE_FLOOR_NW_BOOKSHELF_TILES = {new RSTile(1607, 3824, 1), new RSTile(1607, 3825, 1), new RSTile(1607, 3827, 1), new RSTile(1611, 3831, 1), new RSTile(1612, 3831, 1), new RSTile(1613, 3831, 1), new RSTile(1617, 3831, 1), new RSTile(1618, 3831, 1), new RSTile(1620, 3831, 1), new RSTile(1624, 3831, 1), new RSTile(1624, 3829, 1), new RSTile(1624, 3825, 1), new RSTile(1624, 3824, 1), new RSTile(1624, 3819, 1), new RSTile(1624, 3817, 1), new RSTile(1623, 3816, 1), new RSTile(1621, 3816, 1), new RSTile(1617, 3816, 1), new RSTile(1616, 3816, 1), new RSTile(1611, 3816, 1), new RSTile(1609, 3816, 1), new RSTile(1620, 3820, 1), new RSTile(1620, 3822, 1), new RSTile(1620, 3824, 1), new RSTile(1620, 3825, 1), new RSTile(1620, 3827, 1), new RSTile(1621, 3826, 1), new RSTile(1621, 3822, 1), new RSTile(1621, 3820, 1), new RSTile(1612, 3823, 1),
            new RSTile(1611, 3823, 1),
            new RSTile(1607, 3824, 1),
            new RSTile(1607, 3825, 1),
            new RSTile(1607, 3827, 1),
            new RSTile(1612, 3831, 1),};
    RSTile[] MIDDLE_FLOOR_CENTRE_BOOK_TILES = {new RSTile(1625, 3801, 1), new RSTile(1625, 3802, 1), new RSTile(1625, 3803, 1), new RSTile(1625, 3804, 1), new RSTile(1625, 3806, 1), new RSTile(1625, 3807, 1), new RSTile(1625, 3808, 1), new RSTile(1625, 3809, 1), new RSTile(1625, 3811, 1), new RSTile(1625, 3812, 1), new RSTile(1625, 3813, 1), new RSTile(1625, 3814, 1), new RSTile(1626, 3815, 1), new RSTile(1627, 3815, 1), new RSTile(1631, 3815, 1), new RSTile(1632, 3815, 1), new RSTile(1633, 3815, 1), new RSTile(1634, 3815, 1), new RSTile(1638, 3815, 1), new RSTile(1639, 3815, 1), new RSTile(1640, 3814, 1), new RSTile(1640, 3813, 1), new RSTile(1640, 3803, 1), new RSTile(1640, 3802, 1), new RSTile(1640, 3801, 1), new RSTile(1639, 3800, 1), new RSTile(1638, 3800, 1), new RSTile(1634, 3800, 1), new RSTile(1633, 3800, 1), new RSTile(1632, 3800, 1), new RSTile(1631, 3800, 1), new RSTile(1627, 3800, 1), new RSTile(1626, 3800, 1),};
    RSTile[] MIDDLE_FLOOR_NE_BOOK_TILES = {new RSTile(1641, 3817, 1), new RSTile(1641, 3818, 1), new RSTile(1641, 3819, 1), new RSTile(1641, 3824, 1), new RSTile(1641, 3825, 1), new RSTile(1641, 3829, 1), new RSTile(1645, 3831, 1), new RSTile(1646, 3831, 1), new RSTile(1647, 3831, 1), new RSTile(1648, 3831, 1), new RSTile(1649, 3830, 1), new RSTile(1649, 3828, 1), new RSTile(1650, 3829, 1), new RSTile(1652, 3831, 1), new RSTile(1653, 3831, 1), new RSTile(1658, 3827, 1), new RSTile(1658, 3826, 1), new RSTile(1658, 3823, 1), new RSTile(1658, 3822, 1), new RSTile(1658, 3821, 1), new RSTile(1658, 3820, 1), new RSTile(1656, 3816, 1), new RSTile(1655, 3816, 1), new RSTile(1651, 3816, 1), new RSTile(1649, 3816, 1), new RSTile(1648, 3816, 1), new RSTile(1644, 3816, 1), new RSTile(1643, 3816, 1)};

    RSTile[] BOTTOM_FLOOR_NE_BOOK_TILES = {new RSTile(1639, 3821, 0), new RSTile(1639, 3822, 0), new RSTile(1639, 3827, 0), new RSTile(1639, 3829, 0), new RSTile(1642, 3831, 0), new RSTile(1645, 3831, 0), new RSTile(1646, 3829, 0), new RSTile(1646, 3827, 0), new RSTile(1646, 3826, 0), new RSTile(1647, 3827, 0), new RSTile(1647, 3829, 0), new RSTile(1647, 3830, 0), new RSTile(1652, 3831, 0), new RSTile(1653, 3831, 0), new RSTile(1656, 3831, 0), new RSTile(1658, 3829, 0), new RSTile(1658, 3826, 0), new RSTile(1658, 3825, 0), new RSTile(1658, 3820, 0), new RSTile(1658, 3819, 0), new RSTile(1658, 3816, 0), new RSTile(1655, 3814, 0), new RSTile(1654, 3814, 0), new RSTile(1651, 3817, 0), new RSTile(1651, 3819, 0), new RSTile(1651, 3820, 0), new RSTile(1650, 3821, 0), new RSTile(1650, 3819, 0), new RSTile(1650, 3816, 0), new RSTile(1648, 3814, 0), new RSTile(1646, 3814, 0), new RSTile(1645, 3814, 0),};
    RSTile[] BOTTOM_FLOOR_SW_BOOK_TILES = {new RSTile(1626, 3795, 0), new RSTile(1625, 3793, 0), new RSTile(1623, 3793, 0), new RSTile(1620, 3792, 0), new RSTile(1624, 3792, 0), new RSTile(1626, 3788, 0), new RSTile(1626, 3787, 0), new RSTile(1624, 3784, 0), new RSTile(1623, 3784, 0), new RSTile(1621, 3784, 0), new RSTile(1615, 3785, 0), new RSTile(1615, 3788, 0), new RSTile(1615, 3790, 0), new RSTile(1614, 3790, 0), new RSTile(1614, 3788, 0), new RSTile(1614, 3786, 0), new RSTile(1612, 3784, 0), new RSTile(1610, 3784, 0), new RSTile(1609, 3784, 0), new RSTile(1607, 3786, 0), new RSTile(1607, 3789, 0), new RSTile(1607, 3795, 0), new RSTile(1607, 3796, 0), new RSTile(1607, 3799, 0), new RSTile(1610, 3801, 0), new RSTile(1612, 3801, 0), new RSTile(1618, 3801, 0), new RSTile(1620, 3801, 0),};
    RSTile[] BOTTOM_FLOOR_NW_BOOK_TILES = {new RSTile(1620, 3814, 0), new RSTile(1618, 3814, 0), new RSTile(1617, 3814, 0), new RSTile(1615, 3816, 0), new RSTile(1615, 3817, 0), new RSTile(1615, 3820, 0), new RSTile(1614, 3820, 0), new RSTile(1614, 3817, 0), new RSTile(1614, 3816, 0), new RSTile(1612, 3814, 0), new RSTile(1610, 3814, 0), new RSTile(1607, 3816, 0), new RSTile(1607, 3817, 0), new RSTile(1607, 3820, 0), new RSTile(1607, 3826, 0), new RSTile(1607, 3828, 0), new RSTile(1609, 3831, 0), new RSTile(1612, 3831, 0), new RSTile(1614, 3831, 0), new RSTile(1619, 3831, 0), new RSTile(1621, 3831, 0), new RSTile(1624, 3831, 0), new RSTile(1626, 3829, 0), new RSTile(1626, 3827, 0), new RSTile(1624, 3823, 0), new RSTile(1622, 3823, 0), new RSTile(1620, 3823, 0), new RSTile(1621, 3822, 0), new RSTile(1624, 3822, 0), new RSTile(1626, 3820, 0)};


    RSTile[] TOP_FLOOR_CENTRAL_AREA_BOOK_TILES = {new RSTile(1625, 3802, 2), new RSTile(1625, 3804, 2), new RSTile(1625, 3811, 2), new RSTile(1625, 3812, 2), new RSTile(1625, 3802, 2), new RSTile(1625, 3804, 2), new RSTile(1625, 3811, 2), new RSTile(1625, 3812, 2), new RSTile(1627, 3815, 2), new RSTile(1628, 3815, 2), new RSTile(1635, 3815, 2), new RSTile(1637, 3815, 2), new RSTile(1638, 3815, 2), new RSTile(1640, 3813, 2), new RSTile(1640, 3811, 2), new RSTile(1640, 3810, 2), new RSTile(1638, 3800, 2), new RSTile(1632, 3800, 2), new RSTile(1630, 3800, 2), new RSTile(1629, 3800, 2), new RSTile(1627, 3800, 2),};
    RSTile[] TOP_FLOOR_NW_BOOK_TILES = {new RSTile(1611, 3816, 2), new RSTile(1610, 3816, 2), new RSTile(1609, 3816, 2), new RSTile(1607, 3817, 2), new RSTile(1607, 3819, 2), new RSTile(1607, 3829, 2), new RSTile(1608, 3831, 2), new RSTile(1610, 3831, 2), new RSTile(1611, 3831, 2), new RSTile(1622, 3831, 2), new RSTile(1623, 3831, 2), new RSTile(1624, 3829, 2), new RSTile(1624, 3828, 2), new RSTile(1624, 3821, 2), new RSTile(1624, 3819, 2), new RSTile(1622, 3816, 2), new RSTile(1620, 3816, 2), new RSTile(1618, 3816, 2), new RSTile(1615, 3821, 2), new RSTile(1617, 3821, 2), new RSTile(1619, 3822, 2), new RSTile(1619, 3824, 2), new RSTile(1618, 3826, 2), new RSTile(1617, 3826, 2), new RSTile(1615, 3827, 2), new RSTile(1616, 3827, 2), new RSTile(1618, 3827, 2), new RSTile(1620, 3826, 2), new RSTile(1620, 3824, 2), new RSTile(1620, 3822, 2), new RSTile(1620, 3821, 2), new RSTile(1619, 3820, 2), new RSTile(1617, 3820, 2), new RSTile(1617, 3822, 2), new RSTile(1615, 3820, 2),};
    RSTile[] TOP_FLOOR_NE_BOOK_TILES = {new RSTile(1641, 3818, 2), new RSTile(1641, 3820, 2), new RSTile(1641, 3821, 2), new RSTile(1641, 3829, 2), new RSTile(1643, 3831, 2), new RSTile(1644, 3831, 2), new RSTile(1654, 3831, 2), new RSTile(1656, 3831, 2), new RSTile(1658, 3830, 2), new RSTile(1658, 3828, 2), new RSTile(1658, 3818, 2), new RSTile(1658, 3817, 2), new RSTile(1656, 3816, 2), new RSTile(1655, 3816, 2), new RSTile(1652, 3816, 2), new RSTile(1648, 3817, 2), new RSTile(1648, 3819, 2), new RSTile(1648, 3821, 2), new RSTile(1649, 3823, 2), new RSTile(1650, 3823, 2), new RSTile(1652, 3823, 2), new RSTile(1654, 3822, 2), new RSTile(1654, 3820, 2), new RSTile(1655, 3820, 2), new RSTile(1655, 3821, 2), new RSTile(1655, 3823, 2), new RSTile(1653, 3824, 2), new RSTile(1652, 3824, 2), new RSTile(1649, 3824, 2), new RSTile(1648, 3824, 2), new RSTile(1647, 3822, 2), new RSTile(1647, 3820, 2), new RSTile(1647, 3818, 2), new RSTile(1645, 3816, 2), new RSTile(1644, 3816, 2),};
    RSTile[] TOP_FLOOR_SW_BOOK_TILES = {
            new RSTile(1607, 3785, 2),
            new RSTile(1607, 3786, 2),
            new RSTile(1607, 3796, 2),
            new RSTile(1607, 3797, 2),
            new RSTile(1608, 3799, 2),
            new RSTile(1610, 3799, 2),
            new RSTile(1611, 3799, 2),
            new RSTile(1618, 3799, 2),
            new RSTile(1621, 3799, 2),
            new RSTile(1624, 3797, 2),
            new RSTile(1624, 3795, 2),
            new RSTile(1624, 3794, 2),
            new RSTile(1624, 3792, 2),
            new RSTile(1623, 3791, 2),
            new RSTile(1622, 3791, 2),
            new RSTile(1618, 3792, 2),
            new RSTile(1618, 3793, 2),
            new RSTile(1618, 3794, 2),
            new RSTile(1617, 3793, 2),
            new RSTile(1617, 3792, 2),
            new RSTile(1618, 3790, 2),
            new RSTile(1620, 3790, 2),
            new RSTile(1622, 3790, 2),
            new RSTile(1624, 3789, 2),
            new RSTile(1624, 3788, 2),
            new RSTile(1624, 3786, 2),
            new RSTile(1624, 3785, 2),
            new RSTile(1623, 3784, 2),
            new RSTile(1621, 3784, 2),
            new RSTile(1611, 3784, 2),
            new RSTile(1609, 3784, 2),
            new RSTile(1612, 3789, 2),
            new RSTile(1612, 3791, 2),
            new RSTile(1612, 3794, 2),
            new RSTile(1613, 3793, 2),
            new RSTile(1613, 3792, 2),
            new RSTile(1613, 3791, 2),
            new RSTile(1617, 3791, 2),
            new RSTile(1617, 3793, 2),
            new RSTile(1618, 3794, 2),
            new RSTile(1618, 3792, 2),
            new RSTile(1619, 3791, 2),
            new RSTile(1623, 3791, 2),
            new RSTile(1623, 3790, 2),
            new RSTile(1622, 3790, 2),
            new RSTile(1619, 3790, 2),
    };
    String bookLocation;
    String bookLocationFloor;
    String bookLocationBay;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STAFF_OF_FIRE, 1, 200),
                    new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.AIR_RUNE, 600, 20),
                    new GEItem(ItemID.LOBSTER, 15, 50),

                    new GEItem(ItemID.SKILLS_NECKLACE[0], 1, 20),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 50),

                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        if (!BankManager.checkInventoryItems(LOBSTER, MIND_RUNE, AIR_RUNE,
                ItemID.SKILLS_NECKLACE[0])) {
            buyStep.buyItems();
        }
    }

    public void getItems() {
        if (!BankManager.checkInventoryItems(LOBSTER, MIND_RUNE, AIR_RUNE,
                ItemID.SKILLS_NECKLACE[0])) {
            cQuesterV2.status = "Depths of Despair: Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositEquipment();
            BankManager.depositAll(true);
            BankManager.checkEquippedGlory();
            BankManager.withdraw(12, true, LOBSTER);
            BankManager.withdraw(1, true, STAFF_OF_FIRE);
            BankManager.withdraw(300, true, MIND_RUNE);
            BankManager.withdraw(600, true, AIR_RUNE);
            BankManager.withdraw(1, true, ItemID.ZAMORAK_MONK_BOTTOM);
            BankManager.withdraw(1, true, ItemID.ZAMORAK_MONK_TOP);
            Utils.equipItem(ItemID.ZAMORAK_MONK_TOP);
            Utils.equipItem(ItemID.ZAMORAK_MONK_BOTTOM);
            BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE[0]);
            BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
            BankManager.close(true);
            Utils.equipItem(STAFF_OF_FIRE);
        }
    }

    String[] startDialogue = {"Anything I can help you with?", "Yes."};

    public void startQuest() {
        cQuesterV2.status = "Going to start";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!LARGE_START_AREA.contains(Player.getPosition()))
            PathingUtil.walkToArea(START_AREA);

        if (LARGE_START_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Lord Kandur Hosidius")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(startDialogue); // testing to see if it works w/o having a handleConversation after
            }
        }
    }

    public void step2() {
        cQuesterV2.status = "Step 2- Going to Chef Olivia";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(KITCHEN_AREA);

        if (NpcChat.talkToNPC("Chef Olivia")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step3() {
        cQuesterV2.status = "Step 3- Going to Library";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(BOTTOM_FLOOR_NE_BAY);
        if (BOTTOM_FLOOR_NE_BAY.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Galana")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }


    public void step4() {
        cQuesterV2.status = "Getting book";
        PathingUtil.walkToArea(BOTTOM_FLOOR_NE_BAY);
        if (BOTTOM_FLOOR_NE_BAY.contains(Player.getPosition()) && Inventory.find(BOOK_ID).length < 1) {
            cQuesterV2.status = "Step 4- Getting location";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Galana"))
                NPCInteraction.waitForConversationWindow();
            if (ChatScreen.clickContinue()) ;
            Timer.abc2WaitCondition(() -> Interfaces.get(231, 5) != null, 4000, 6000);
            Optional<String> chat = ChatScreen.getMessage();
            if (chat.isPresent()) {
                try {
                    bookLocation = chat.get().split("Try the ")[1];//Interfaces.get(231, 5).getText().split("Try the ")[1];
                    bookLocationFloor = bookLocation.split("floor,")[0]; // will be bottom or top
                    bookLocationBay = bookLocation.split("floor,")[1];
                    General.println("[Debug]: Book location floor is " + bookLocationFloor);
                    General.println("[Debug]: Book location bay is " + bookLocationBay);
                } catch (Exception e) {
                    General.println("[Debug]: Failed to get location");
                    return;
                }
            }
            /**
             * TOP FLOOR
             */
            if (bookLocationFloor.contains("top")) {
                if (bookLocationBay.contains("south western")) {
                    General.println("[Debug]: Going to top floor, south western");
                    searchBookTileArray(TOP_FLOOR_SW_BOOK_TILES, TOP_FLOOR_SW_BAY);
                } else if (bookLocationBay.contains("north-eastern")) {
                    General.println("[Debug]: Going to top floor, north eastern");
                    searchBookTileArray(TOP_FLOOR_NE_BOOK_TILES, TOP_FLOOR_NE_BAY);

                } else if (bookLocationBay.contains("north western")) {
                    General.println("[Debug]: Going to top floor, north western");
                    searchBookTileArray(TOP_FLOOR_NW_BOOK_TILES, TOP_FLOOR_NW_BAY);

                } else if (bookLocationBay.contains("central")) {
                    General.println("[Debug]: Going to top floor, central");
                    searchBookTileArray(TOP_FLOOR_CENTRAL_AREA_BOOK_TILES, TOP_FLOOR_CENTRE_BAY);
                } else if (bookLocationBay.contains("north eastern")) {
                    General.println("[Debug]: Going to top floor, north-eastern bay");
                    searchBookTileArray(TOP_FLOOR_NE_BOOK_TILES, TOP_FLOOR_NE_BAY);
                }

                /**
                 * BOTTOM FLOOR
                 * */
            } else if (bookLocationFloor.contains("bottom")) {
                if (bookLocationBay.contains("south western")) {
                    General.println("[Debug]: Going to bottom floor, south western bay");
                    PathingUtil.walkToArea(BOTTOM_SW_BAY);
                    searchBookTileArray(BOTTOM_FLOOR_SW_BOOK_TILES, BOTTOM_SW_BAY);

                } else if (bookLocationBay.contains("north eastern")) {
                    General.println("[Debug]: Going to bottom floor, North eastern bay");
                    goToLibrarySpot(BOTTOM_FLOOR_NE_BAY);
                    searchBookTileArray(BOTTOM_FLOOR_NE_BOOK_TILES, BOTTOM_FLOOR_NE_BAY);

                } else if (bookLocationBay.contains("north western")) {
                    General.println("[Debug]: Going to bottom floor, north western");
                    searchBookTileArray(BOTTOM_FLOOR_NW_BOOK_TILES, BOTTOM_NW_BAY);
                }

                /**
                 * MIDDLE FLOOR
                 */
            } else if (bookLocationFloor.contains("middle")) {
                if (bookLocationBay.contains("north western")) {
                    General.println("[Debug]: Going to middle floor, north western bay");
                    PathingUtil.walkToArea(MIDDLE_FLOOR_NW_BAY);
                    searchBookTileArray(MIDDLE_FLOOR_NW_BOOKSHELF_TILES, MIDDLE_FLOOR_NW_BAY);

                } else if (bookLocationBay.contains("south western")) {
                    General.println("[Debug]: Going to bottom floor, south-western bay");
                    PathingUtil.walkToArea(MIDDLE_FLOOR_SW_BAY);
                    searchBookTileArray(MIDDLE_FLOOR_SW_BOOKSHELF_TILES, MIDDLE_FLOOR_SW_BAY);


                } else if (bookLocationBay.contains("north eastern")) {
                    General.println("[Debug]: Going to bottom floor, north-eastern bay");
                    PathingUtil.walkToArea(MIDDLE_FLOOR_NE_BAY);
                    searchBookTileArray(MIDDLE_FLOOR_NE_BOOK_TILES, MIDDLE_FLOOR_NE_BAY);

                } else if (bookLocationBay.contains("central area")) {
                    General.println("[Debug]: Going to middle floor, central bay");
                    PathingUtil.walkToArea(MIDDLE_FLOOR_CENTRE_AREA);
                    searchBookTileArray(MIDDLE_FLOOR_CENTRE_BOOK_TILES, MIDDLE_FLOOR_CENTRE_AREA);

                }
            }
        }
    }

    RSArea MIDDLE_FLOOR_NE_BAY = new RSArea(new RSTile(1658, 3816, 1), new RSTile(1641, 3831, 1));
    RSTile MIDDLE_FLOOR_SW_TILE = new RSTile(1610, 3785, 1);
    RSTile MIDDLE_FLOOR_NE_TILE = new RSTile(1657, 2828, 1);

    public void goToLibrarySpot(RSArea area) {
        if (!area.contains(Player.getPosition())) {
            PathingUtil.walkToArea(area, false);
            Timer.abc2WaitCondition(() -> area.contains(Player.getPosition()), 12000, 15000);
        }
    }

    /**
     * Using the Runelite API i got the specific tiles of the bookselves that actually may contain books at any given time.
     * The goalis to decrease time looking and therefore detection
     * <p>
     * Tested once, works well plus more efficient (by alot)
     *
     * @param array
     * @param area
     */

    public void searchBookTileArray(RSTile[] array, RSArea area) {
        PathingUtil.walkToArea(area);
        if (area.contains(Player.getPosition())) {
            for (int i = 0; i < array.length; i++) {
                RSObject[] obj = Objects.findNearest(20, Filters.Objects.tileEquals(array[i]));
                if (obj.length > 0) {
                    if (obj[0].getPosition().distanceTo(Player.getPosition()) > 7 || !obj[0].isClickable())
                        if (PathingUtil.clickScreenWalk(array[i]))
                            Timer.slowWaitCondition(() -> obj[0].getPosition().distanceTo(Player.getPosition()) < 6, 6000, 9000);

                    if (!obj[0].isClickable())
                        obj[0].adjustCameraTo();

                    if (AccurateMouse.click(obj[0], "Search")) {
                        Timer.waitCondition(() -> Player.getAnimation() != -1, 5000, 8000);
                        Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 5000, 8000);
                        Utils.microSleep(); //
                        if (NPCInteraction.isConversationWindowUp()) {
                            NPCInteraction.handleConversation();
                            Utils.modSleep();
                        }
                    }

                    if (Inventory.find(BOOK_ID).length > 0)
                        break;
                }
            }
        }
    }

    public void step5() {
        cQuesterV2.status = "Step 5- Reading book";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(BOOK_ID).length > 0)
            if (AccurateMouse.click(Inventory.find(BOOK_ID)[0], "Read"))
                Timer.abc2WaitCondition(() -> Interfaces.get(26, 70) != null, 5000, 9000);

        if (Interfaces.get(26, 70) != null)
            if (Interfaces.get(26, 70).click())
                Utils.modSleep();
    }

    public void step6() {
        cQuesterV2.status = "Step 6- Going to Cave";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!CAVE_ENTRANCE.contains(Player.getPosition()) && !WHOLE_CAVE.contains(Player.getPosition())) {
            if (Inventory.find(ItemID.SKILLS_NECKLACE).length > 0 && !WC_GUILD_TELE_AREA.contains(Player.getPosition())) {
                if (AccurateMouse.click(Inventory.find(ItemID.SKILLS_NECKLACE)[0], "Rub")) {
                    Timer.abc2WaitCondition(() -> Interfaces.get(187, 3, 4) != null, 4000, 6000);
                    Utils.shortSleep();
                }
                if (Interfaces.get(187, 3, 4) != null)
                    if (Interfaces.get(187, 3, 4).click())
                        Waiting.waitUniform(4000, 9000);

            }
            PathingUtil.walkToArea(CAVE_ENTRANCE);
        }
        if (CAVE_ENTRANCE.contains(Player.getPosition()) && !WHOLE_CAVE.contains(Player.getPosition())) {
            if (Utils.clickObject(31690, "Enter", false))
                Timer.abc2WaitCondition(() -> CAVE_BOTTOM_OF_ENTRANCE_LADDER.contains(Player.getPosition()), 10000, 15000);

        }
    }

    RSArea LARGE_PRE_SHORTCUT_2 = new RSArea(new RSTile(1703, 9810, 0), new RSTile(1724, 9797, 0));

    public void step7() {
        if (WHOLE_CAVE.contains(Player.getPosition()) && CAVE_BOTTOM_OF_ENTRANCE_LADDER.contains(Player.getPosition()))
            PathingUtil.walkToArea(PRE_SHORTCUT_2);

        if (WHOLE_CAVE.contains(Player.getPosition()) && LARGE_PRE_SHORTCUT_2.contains(Player.getPosition())) {
            RSObject[] stones = Objects.findNearest(20, 31699);
            cQuesterV2.status = "Step 7- Navigating stones";
            General.println("[Debug]: " + cQuesterV2.status);
            if (stones.length > 1) {
                if (AccurateMouse.click(stones[0], "Cross"))
                    Timer.abc2WaitCondition(() -> POST_STONES.contains(Player.getPosition()), 7000, 10000);
            }
        }
        if (WHOLE_CAVE.contains(Player.getPosition()) && POST_STONES.contains(Player.getPosition()))
            PathingUtil.walkToArea(CAVE_LADDER);

        if (WHOLE_CAVE.contains(Player.getPosition()) && CAVE_LADDER_LARGE.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 7- Climbing down tunnel";
            General.println("[Debug]: " + cQuesterV2.status);

            if (Utils.clickObject(31692, "Climb-down", false))
                Timer.abc2WaitCondition(() -> NPCs.findNearest(7899).length > 0, 7000, 9000);
        }
    }

    public void step8() {
        cQuesterV2.status = "Step 8 - Talking to Artur";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC(7899)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void step9() {
        cQuesterV2.status = "Step 9 - Killing snake";
        General.println("[Debug]: " + cQuesterV2.status);

        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);


        RSObject[] rocks = Objects.findNearest(25, 31698);
        if (rocks.length > 0 && Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE) && !Combat.isUnderAttack()) {
            PathingUtil.webWalkToTile(rocks[0].getPosition());
            if (!Combat.isUnderAttack() &&
                    AccurateMouse.click(rocks[0], "Climb"))
                Waiting.waitNormal(3000, 5000);
        }

        RSNPC[] sandSnake = NPCs.find("Sand snake");
        long currentTime = System.currentTimeMillis();

        while (sandSnake.length > 0 && Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
            General.sleep(150);
            if (!Combat.isUnderAttack() || !sandSnake[0].isInCombat()) {
                if (AccurateMouse.click(sandSnake[0], "Attack")) {
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 4000, 6000);
                    CombatUtil.waitUntilOutOfCombat("Sand snake", 45);
                }

            } else if (Combat.isUnderAttack())
                CombatUtil.waitUntilOutOfCombat("Sand snake", 45);

            Utils.shortSleep();

            if (Combat.getHPRatio() < General.random(35, 60) && Inventory.find(LOBSTER).length > 0)
                if (AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat"))
                    Utils.microSleep();

            if (currentTime + General.random(45000, 80000) > System.currentTimeMillis())
                break;

            if (sandSnake.length < 1) {
                Utils.modSleep();
                break;
            }
        }
    }

    public void step10() {
        RSNPC[] sandSnake = NPCs.find(7903);
        if (sandSnake.length < 1) {
            cQuesterV2.status = "Step 10: Searching chest";
            General.println("[Debug]: " + cQuesterV2.status);
            RSObject[] chest = Objects.find(20, "Chest");
            if (chest.length > 0) {
                General.println("[Debug]: Walking to Chest");
                PathingUtil.localNavigation(chest[0].getPosition());
            }
            if (Utils.clickObject(31703, "Search", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    int CERTIFICATE = 21759;

    public void claimFavour() {
        RSItem[] invCert = Inventory.find(CERTIFICATE);
        if (invCert.length > 0) {
            invCert[0].click("Read");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes.");
        }
    }

    public void step11() {
        cQuesterV2.status = "Step 11: Going to Finish";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!START_AREA.contains(Player.getPosition()) && Inventory.find(21758).length > 0) {
            if (Inventory.find(ItemID.SKILLS_NECKLACE).length > 0 && !WC_GUILD_TELE_AREA.contains(Player.getPosition())) {
                if (AccurateMouse.click(Inventory.find(ItemID.SKILLS_NECKLACE)[0], "Rub"))
                    Timer.abc2WaitCondition(() -> Interfaces.get(187, 3, 4) != null, 5000, 9000);

                if (Interfaces.get(187, 3, 4) != null)
                    if (Interfaces.get(187, 3, 4).click())
                        Waiting.waitUniform(2000, 3500);

            }
            PathingUtil.walkToArea(START_AREA, false);
        }
        if (LARGE_START_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Lord Kandur Hosidius")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    int RADAS_JOURNEY = 13533;
    int TRANSVERGENCE_THEORY = 13534;
    int IDEOLOGY_OF_DARKNESS = 13532;
    int SOUL_JOURNEY = 19637;
    int VALAMORE_ENVOY = 21756;
    int HOSIDIUS_LETTER = 13528;
    int SOMETHING = 13526;


    private void dropBooks() {
        cQuesterV2.status = "Dropping books";
        Inventory.drop(RADAS_JOURNEY, HOSIDIUS_LETTER, TRANSVERGENCE_THEORY, IDEOLOGY_OF_DARKNESS, SOUL_JOURNEY, VALAMORE_ENVOY, SOMETHING);
    }

    private Timer safteyTimer = new Timer(General.random(480000, 600000)); // 8- 10min

    int GAME_SETTING = 1671;
    final int startSetting = Game.getSetting(GAME_SETTING);

    @Override
    public void execute() {
        if (Game.getSetting(1671) == 0) {
            buyItems();
            getItems();
            startQuest();
        } else if (Game.getSetting(1671) == 1) {
            step2();
        } else if (Game.getSetting(1671) == 2) {
            step3();
        } else if (Game.getSetting(1671) == 3) {
            step4();
            step5();
        } else if (Game.getSetting(1671) == 4) {
            step6();
        } else if (Game.getSetting(1671) == 5) {
            step6();
        } else if (Game.getSetting(1671) == 6) {
            step6();
            step7();
        } else if (Game.getSetting(1671) == 7) {
            step8();
        } else if (Game.getSetting(1671) == 8) {
            step9();
            step10();
        } else if (Game.getSetting(1671) == 9) {
            step10();
        } else if (Game.getSetting(1671) == 10 || Game.getSetting(1671) == 266) {
            step11();
        } else if (Game.getSetting(1671) == 75 || Game.getSetting(1671) == 203
                || Game.getSetting(1671) == 331) {
            Utils.closeQuestCompletionWindow();
            claimFavour();
            dropBooks();
            cQuesterV2.taskList.remove(this);
        } else {
            cQuesterV2.taskList.remove(this);
        }

    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }


    private static DepthsOfDespair quest;

    public static DepthsOfDespair get() {
        return quest == null ? quest = new DepthsOfDespair() : quest;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }


    @Override
    public String questName() {
        return "Depths of Despair";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.MAGIC.getActualLevel() >= 13 &&
                Skills.SKILLS.AGILITY.getActualLevel() >= 18;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.THE_DEPTHS_OF_DESPAIR.getState().equals(Quest.State.COMPLETE);
    }
}
