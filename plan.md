# Home Screen Update Plan

## Tasks

### 1. Collapsible Header Optimization
- **Status:** Done
- **What:** The `NestedScrollConnection` + `layout` modifier approach is already solid. No structural changes needed.
- **Done:** Removed the dead `pagerState` dependency from the header area during the unified-scroll refactor.

---

### 2. Unified Single-LazyColumn Scrolling (Emergency Card Scrolls With Content)
- **Status:** Done
- **Problem:** The old layout had two separate scroll containers:
  - Outer `LazyColumn` (emergency card + tab row) — not scrollable in practice
  - Inner `LazyColumn` inside `HorizontalPager` (notice items) — the real scroll
  - Result: emergency card was "pinned" above the pager; scrolling up in notices never revealed/hid the emergency card naturally.
- **Fix:** Merged everything into a single `LazyColumn`:
  - `item { EmergencyCard }` — scrolls away when user scrolls down
  - `stickyHeader { TabRow }` — sticks at top once emergency card scrolls off
  - `items(currentItems)` — current tab's paged notices rendered directly
- **Removed:** `HorizontalPager`, `rememberPagerState`, `NoticePageList` composable
- **Added:** `selectedTabIndex` state (simple Int); tab clicks also scroll back to sticky header position

---

### 3. Remove Padding/Space Between Tab Row and Notice List
- **Status:** Done
- **Problem:** The outer `LazyColumn` had `contentPadding = PaddingValues(bottom = 80.dp)`. Since that LazyColumn only had 2 items (emergency card + tab row), the 80 dp appeared as visible empty space between the tab row and the `HorizontalPager` below it.
- **Fix:** Moved the `contentPadding = PaddingValues(bottom = 80.dp)` to the new unified `LazyColumn` (where it correctly pads below the last notice item for the bottom nav bar).

---

---

### 4. Swipeable Tabs (HorizontalPager — Option B)
- **Status:** Done
- **Approach:** Re-introduced `HorizontalPager` + `pagerState` for follow-the-finger swipe. Emergency card moved into the collapsible Box so it collapses with the header (same visible effect as scrolling).
- **NestedScrollConnection change:** Split into two responsibilities:
  - `onPreScroll` — only handles **downward** scroll: collapses the top section before notices start scrolling
  - `onPostScroll` — only handles **upward** leftover: expands the top section *after* the inner `LazyColumn` reaches its top (inner scrolls first, then section re-appears)
- **TabRow** sits permanently between the collapsible section and the pager — always visible, always acts as sticky.
- Each pager page owns its own `LazyColumn` with `contentPadding(bottom = 80.dp)` for the bottom nav bar.

---

## Layout Architecture (After)

```
Scaffold
  Box  ← nestedScroll (header collapse)
    Column  ← fillMaxSize
      Box  ← collapsible header (layout + onGloballyPositioned)
        Column
          HeaderSection
          CustomSearchBar
      LazyColumn  ← fillMaxSize, single scroll container
        item        → EmergencyAlertCard  (scrolls away)
        stickyHeader → TabRow             (sticks at top)
        items       → NormalNotice cards  (current tab, paged)
```
