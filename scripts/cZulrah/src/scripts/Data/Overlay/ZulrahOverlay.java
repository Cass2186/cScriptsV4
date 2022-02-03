package scripts.Data.Overlay;


import java.awt.*;


public class ZulrahOverlay
{
    private static final Color TILE_BORDER_COLOR = new Color(0, 0, 0, 100);
    private static final Color NEXT_TEXT_COLOR = new Color(255, 255, 255, 100);



/*

    public Dimension render(Graphics2D graphics) {
        ZulrahInstance instance = plugin.getInstance();

        if (instance == null)
        {
            return null;
        }

        ZulrahPhase currentPhase = instance.getPhase();
        ZulrahPhase nextPhase = instance.getNextPhase();
        if (currentPhase == null)
        {
            log.debug("Current phase is NULL");
            return null;
        }

        LocalTile startTile = instance.getStartLocation();
        if (nextPhase != null && currentPhase.getStandLocation() == nextPhase.getStandLocation())
        {
            drawStandTiles(graphics, startTile, currentPhase, nextPhase);
        }
        else
        {
            drawStandTile(graphics, startTile, currentPhase, false);
            drawStandTile(graphics, startTile, nextPhase, true);
        }
        drawZulrahTileMinimap(graphics, startTile, currentPhase, false);
        drawZulrahTileMinimap(graphics, startTile, nextPhase, true);

        return null;
    }

    private void drawStandTiles(Graphics2D graphics, LocalTile startTile, ZulrahPhase currentPhase, ZulrahPhase nextPhase)
    {
        LocalTile localTile = currentPhase.getStandTile(startTile);
        Polygon northPoly = getCanvasTileNorthPoly( localTile);
        Polygon southPoly = getCanvasTileSouthPoly( localTile);
        Polygon poly = Perspective.getCanvasTilePoly( localTile);
        Point textLoc = Perspective.getCanvasTextLocation( graphics, localTile, "Stand / Next", 0);
        if (northPoly != null && southPoly != null && poly != null && textLoc != null)
        {
            Color northColor = currentPhase.getColor();
            Color southColor = nextPhase.getColor();
            graphics.setColor(northColor);
            graphics.fillPolygon(northPoly);
            graphics.setColor(southColor);
            graphics.fillPolygon(southPoly);
            graphics.setColor(TILE_BORDER_COLOR);
            graphics.setStroke(new BasicStroke(2));
            graphics.drawPolygon(poly);
            graphics.setColor(NEXT_TEXT_COLOR);
            graphics.drawString("Stand / Next", textLoc.getX(), textLoc.getY());
        }
        if (nextPhase.isJad())
        {
            BufferedImage jadPrayerImg = ZulrahImageManager.getProtectionPrayerBufferedImage(nextPhase.getPrayer());
            if (jadPrayerImg != null)
            {
                Point imageLoc = Perspective.getCanvasImageLocation( localTile, jadPrayerImg, 0);
                if (imageLoc != null)
                {
                    graphics.drawImage(jadPrayerImg, imageLoc.getX(), imageLoc.getY(), null);
                }
            }
        }
    }

    private void drawStandTile(Graphics2D graphics, LocalTile startTile, ZulrahPhase phase, boolean next)
    {
        if (phase == null)
        {
            log.debug("phase null");
            return;
        }

        LocalTile localTile = phase.getStandTile(startTile);
        Polygon poly = Perspective.getCanvasTilePoly( localTile);
        Color color = phase.getColor();
        if (poly != null)
        {
            graphics.setColor(TILE_BORDER_COLOR);
            graphics.setStroke(new BasicStroke(2));
            graphics.drawPolygon(poly);
            graphics.setColor(color);
            graphics.fillPolygon(poly);
            Point textLoc = Perspective.getCanvasTextLocation( graphics, localTile, (next) ? "Next" : "Stand", 0);
            if (textLoc != null)
            {
                graphics.setColor(NEXT_TEXT_COLOR);
                graphics.drawString((next) ? "Next" : "Stand", textLoc.getX(), textLoc.getY());
            }
        }
        else
        {
            log.debug("poly null");
        }

    }

    private void drawZulrahTileMinimap(Graphics2D graphics, LocalTile startTile, ZulrahPhase phase, boolean next)
    {
        if (phase == null)
        {
            return;
        }
        LocalTile zulrahLocalTile = phase.getZulrahTile(startTile);
        Point zulrahMinimapPoint = Perspective.localToMinimap( zulrahLocalTile);
        Color color = phase.getColor();
        graphics.setColor(color);
        if (zulrahMinimapPoint != null)
        {
            graphics.fillOval(zulrahMinimapPoint.getX() - 2, zulrahMinimapPoint.getY() - 2, 4, 4);
        }
        graphics.setColor(TILE_BORDER_COLOR);
        graphics.setStroke(new BasicStroke(1));
        if (zulrahMinimapPoint != null)
        {
            graphics.drawOval(zulrahMinimapPoint.getX() - 2, zulrahMinimapPoint.getY() - 2, 4, 4);
        }
        if (next)
        {
            graphics.setColor(NEXT_TEXT_COLOR);
            FontMetrics fm = graphics.getFontMetrics();
            if (zulrahMinimapPoint != null)
            {
                graphics.drawString("Next", zulrahMinimapPoint.getX() - fm.stringWidth("Next") / 2, zulrahMinimapPoint.getY() - 2);
            }
        }
    }

    private Polygon getCanvasTileNorthPoly( LocalTile localLocation)
    {
        int plane = MyPlayer.getPosition().getPlane();
        int halfTile = Projection.getTileBounds(
                new RSTile(localLocation.getX(), localLocation.getY()) ,0)
                .length;

        Point p1 = Projection.tileToScreen(
                new RSTile(localLocation.getX() - halfTile, localLocation.getY() - halfTile), plane);
        Point p2 = Projection.tileToScreen( new RSTile(localLocation.getX() - halfTile, localLocation.getY() + halfTile), plane);
        Point p3 = Projection.tileToScreen( new RSTile(localLocation.getX() + halfTile, localLocation.getY() + halfTile), plane);

        if (p1 == null || p2 == null || p3 == null)
        {
            return null;
        }

        Polygon poly = new Polygon();
        poly.addPoint((int) p1.getX(), (int) p1.getY());
        poly.addPoint((int) p2.getX(),(int)  p2.getY());
        poly.addPoint((int) p3.getX(), (int) p3.getY());

        return poly;
    }

    private Polygon getCanvasTileSouthPoly(LocalTile localLocation)
    {
        int plane = MyPlayer.getPosition().getPlane();
        int halfTile = Perspective.LOCAL_TILE_SIZE / 2;

        Point p1 = Projection.tileToScreen(
                new RSTile(localLocation.getX() - halfTile, localLocation.getY() - halfTile), plane);
        Point p2 = Projection.tileToScreen( new RSTile(localLocation.getX() + halfTile, localLocation.getY() + halfTile), plane);
        Point p3 = Projection.tileToScreen( new RSTile(localLocation.getX() + halfTile, localLocation.getY() - halfTile), plane);

        if (p1 == null || p2 == null || p3 == null)
        {
            return null;
        }

        Polygon poly = new Polygon();
        poly.addPoint((int) p1.getX(),(int)  p1.getY());
        poly.addPoint((int) p2.getX(),(int)  p2.getY());
        poly.addPoint((int) p3.getX(),(int)  p3.getY());

        return poly;
    }*/

}