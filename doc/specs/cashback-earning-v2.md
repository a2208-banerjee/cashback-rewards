# Example Map: Earning Cashback on Purchases

## Story

As a customer, I want to earn cashback on my purchases, so that I am rewarded for my loyalty.

## Known constraints (given)

- All amounts are in USD and are rounded half-up to the cent.
- "Month" means a calendar month in the customer's timezone.

## Notes

- **Must** is a rule from the draft or one that follows directly from it. **Should** is a proposal the business can change.
- Where a table is used, a boundary row serves as the counter-example. The table carries the scenarios, so they are not repeated as bullets.
- "(Q)" marks an answer that depends on an open question under the same rule.

## What changed from the draft rules

- "Qualifying spend" was undefined, so it became its own rule (Rule 2).
- The 1% and 2% rates, and rounding, are in one rule (Rule 1). The tier that applies at a given time is Rule 3.
- The cap is split from the month boundary (Rules 4 and 5).
- Refunds are split from how a refund interacts with the cap (Rules 6 and 7).

---

## Rule 1: Must pay 1% (Standard) or 2% (Premium) of qualifying spend, rounded half-up to the cent on each purchase

| Tier | Purchase | Exact amount | Cashback |
|---|---|---|---|
| Standard | $120.00 | $1.20 | $1.20 |
| Premium | $120.00 | $2.40 | $2.40 |
| Standard | $12.50 | $0.125 | $0.13 (exactly half, so rounds up) |
| Standard | $0.49 | $0.0049 | $0.00 (counter-example: below half a cent, so nothing is earned) |

- Questions:
  - Is rounding applied to each purchase or to the monthly total? Three $0.50 purchases give $0.03 one way and $0.02 the other. Proposed: each purchase.
  - Are the rates flat for every merchant category? (See "Conflict with the existing build" below.)

## Rule 2: Must only reward qualifying spend, which is a completed purchase of goods or services

| Transaction | Cashback |
|---|---|
| Completed purchase of $100.00 (Standard) | $1.00 |
| Purchase still pending, then completed | $0.00 while pending, $1.00 once completed |
| Declined purchase | $0.00 |
| Cash advance or card fee | $0.00 (counter-example: money moved, but it is not a purchase) |

- Questions:
  - Do taxes, tips, shipping, gift cards, balance transfers and wallet top-ups count?
  - Are there merchants or categories that never earn?
  - Is there a minimum purchase amount?

## Rule 3: Should apply the member's tier at the time of purchase

- Example: The one where a member is Standard on 10 Oct, upgrades on 15 Oct, and buys $100.00 on each of those dates. The 10 Oct purchase earns $1.00 and the 20 Oct purchase earns $2.00.
- Counter-example: The one where a Premium member buys $100.00 and earns $2.00, then downgrades. The $2.00 stays. A tier change is never applied to earlier purchases.
- Questions:
  - How does a member become Premium, and how do they lose it?
  - Is there a grace period after a lapse?

## Rule 4: Must not pay more than $50.00 of cashback per member per month, and must credit the part of a purchase that still fits

| Earned so far this month | Purchase (Standard) | Would earn | Credited |
|---|---|---|---|
| $10.00 | $100.00 | $1.00 | $1.00 (under the cap, so paid in full) |
| $49.50 | $200.00 | $2.00 | $0.50 (only the part that fits) |
| $50.00 | $100.00 | $1.00 | $0.00 (the purchase goes ahead, but earns nothing) |
| $50.00 earned last month, $0.00 this month | $100.00 | $1.00 | $1.00 (the cap starts again each month) |

- Questions:
  - Is the cap per member (all cards) or per card? Proposed: per member.
  - Is the cap the same for Standard and Premium? A Premium member reaches it at $2,500.00 of spend, and a Standard member at $5,000.00.
  - Should the member be told when they are close to or at the cap?

## Rule 5: Must decide which month a purchase belongs to by the customer's local date

- Example: The one where a customer in Los Angeles buys at 9:00 pm on 31 Oct. In UTC it is already 1 Nov, but the purchase counts in October.
- Counter-example: The one where a customer moves to a new timezone during the month. Purchases already assigned to a month stay where they were.
- Questions:
  - Is the date the time of purchase or the date it posts?
  - Where does the customer's timezone come from, and can they change it?

## Rule 6: Must reverse the cashback that was actually earned when a purchase is refunded

| Purchase and cashback credited | Refund | Reversal |
|---|---|---|
| $120.00, $1.20 credited | All $120.00 | $1.20 |
| $120.00, $1.20 credited | $30.00 | $0.30 (a matching share) |
| $100.00 as Premium, $2.00 credited, member since downgraded to Standard | All $100.00 | $2.00 (the rate at purchase, not today's) |
| $200.00, $0.50 credited because of the cap | All $200.00 | $0.50 (counter-example: only what was credited, not the $2.00 it would have earned) |
| $0.50, $0.01 credited | $0.25, then another $0.25 | $0.01 in total (counter-example: the total reversed never exceeds what was earned) |

- Example: The one where a purchase made in October is refunded in November. The reversal is recorded in November, and October does not change.
- Questions:
  - Can a month's cashback go negative? Proposed: carry the amount forward against future cashback.
  - What if the cashback has already been paid out or spent?
  - Is a chargeback or dispute treated like a refund?

## Rule 7: Should give cap room back when a refund happens in the same month

- Example: The one where a member is at the $50.00 cap, including $20.00 from a $2,000.00 purchase. They refund that purchase, so net cashback drops to $30.00. A later $1,000.00 purchase the same month earns $10.00, and the month ends at $40.00.
- Counter-example: The one where a member refunds, in November, a purchase from October. The November cap does not increase, because the reversal belongs to an earlier month.
- Questions:
  - Does the business agree that the cap applies to net cashback for the month?

---

## Conflict with the existing build

`RewardService` currently pays category-based rates (groceries 3%, dining 4%, gas 2%, travel 5%, entertainment 2%, other 1%), has no Premium tier and no monthly cap, and keeps 4 decimal places instead of whole cents. This map follows the draft rules. If category rates are intended, Rules 1 and 2 need to be revisited.

## Out of scope

- Redeeming or paying out cashback
- How a member becomes Premium
- Promotions and bonus cashback
