package sunmade.jcircuit.model.scheme;

import sunmade.jcircuit.model.element.View;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Scheme {
    private final Set<View> views = new HashSet<>();
    private String name;

    public void addView(View view) {
        views.add(Objects.requireNonNull(view));
    }

    public void removeView(View view) {
        views.remove(Objects.requireNonNull(view));
    }

    public Optional<View> getView(int x, int y) {
       for (View view : views) {
           x -= view.getX() - view.getImage().getWidth() / 2.0;
           y -= view.getY() - view.getImage().getHeight() / 2.0;
           if (x > 0 & x < view.getImage().getWidth() & y > 0 & y < view.getImage().getHeight()) {
               return Optional.of(view);
           }
       }
       return Optional.empty();
    }

    public boolean isOverlapping(View view) {
        if (view == null) throw new NullPointerException();
        for (View currentView : views) {
            if (currentView == view) continue;
            int distanceX = Math.abs(currentView.getX() - view.getX());
            int distanceY = Math.abs(currentView.getY() - view.getY());
            double halfWidths = (currentView.getImage().getWidth() + view.getImage().getWidth()) / 2.0;
            double halfHeights = (currentView.getImage().getHeight() + view.getImage().getHeight()) / 2.0;
            if (distanceX < halfWidths & distanceY < halfHeights) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }
}
