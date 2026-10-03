# Example Map: Earn Cashback on Purchases

## Story

As a customer
I want to earn cashback on my purchases
so that I am rewarded for my loyalty.

## Draft rules (what we already know)
- Standard cashback is 1% of qualifying spend
- Premium members earn 2%
- Monthly cashback is capped at $50 per member
- Refunds must reverse any cashback earned
## Known constraints
- All amounts in USD, round half-up to cent
- "Month" = calendar month, customer timezone
###

## How to read this map

- Rules start with "Should" (a proposal the business can change) or "Must" (a rule the business can't compromise on).
- Dollar figures are illustrative. A flat 1% rate is used in examples only so the arithmetic can be checked. The real rates, caps and tiers are open questions.
- A counter-example is a valid edge case, not a bug. The system behaves correctly in it, even if the result surprises the customer.

---

## Rule 1: Must earn cashback on every completed purchase of $10.00 or more at a merchant that is not excluded

Cashback is earned when the purchase completes, not when it is authorized. The customer sees it only after completion.

- Example: The one where a customer buys $80.00 of groceries and the purchase completes, so cashback is earned on $80.00.
- Example: The one where a customer buys online, and the purchase is shipped and charged three days later, so cashback is earned, and shown to the customer, when the charge completes.
- Example: The one where a hotel holds $200.00 on the card, and the final bill is $162.00, so the customer sees nothing during the stay, and cashback is earned on $162.00 at completion.
- Example: The one where a customer buys exactly $10.00, so the minimum is met and cashback is earned.
- Counter-example: The one where a purchase is declined, so no cashback is earned.
- Counter-example: The one where a purchase is authorized but never completes, and the hold drops off, so no cashback is earned and none is ever shown.
- Counter-example: The one where a customer buys $9.99, so the purchase is below the $10.00 minimum and earns nothing.
- Counter-example: The one where a customer makes two $6.00 purchases on the same day, so each is below the minimum and neither earns. They are not added together.
- Counter-example: The one where a customer buys $50.00 at a merchant or in a category the business has excluded, so no cashback is earned.
- Decided:
  - Cashback is earned at completion. The customer sees it after the purchase completes.
  - Some merchants or categories are excluded from earning. The business will define them.
  - The minimum purchase amount to earn cashback is $10.00.
- Questions:
  - Which merchants or categories are excluded? The business owes us this list before the rule can be tested end to end.
  - Is the $10.00 minimum checked against the amount before or after coupons, gift cards and other discounts? Rule 2 shows a $100.00 purchase where only $90.00 or $60.00 is charged to the card.
  - Is the minimum checked on the whole purchase or only on the part that qualifies? Think of a $12.00 purchase that includes $3.00 of an excluded item.
  - If a customer returns part of a purchase and what is left is below $10.00, is the cashback on the remainder still kept? (This links to Rule 7.)
  - Does the minimum apply to every customer, or is it the same for standard and premium members?
  - When the list of excluded merchants changes, does it apply to purchases that were completed before the change?

## Rule 2: Must calculate cashback as a percentage of the qualifying amount

- Example: The one where a customer spends $100.00 at 1% and earns $1.00.
- Example: The one where a customer spends $19.99 at 1% and earns $0.20, since $0.1999 rounds up.
- Counter-example: The one where a $100.00 purchase is paid with a $10.00 coupon, so cashback is earned on the $90.00 actually charged.
- Counter-example: The one where a $100.00 purchase is paid $60.00 by card and $40.00 by gift card, so cashback is earned on the $60.00 paid by card.
- Questions:
  - Is the percentage taken before or after tax, tips, shipping and discounts?
  - Is the rate one flat figure, or does it depend on merchant or category?
  - How are split payments across several cards handled?

## Rule 3: Must round every cashback amount to the cent, using one consistent rule

- Example: The one where a $12.50 purchase at 1% is $0.125, which rounds up to $0.13 (half-up).
- Example: The one where a $0.50 purchase at 1% is $0.005, which rounds up to $0.01.
- Counter-example: The one where a $0.40 purchase at 1% is $0.004, which rounds to $0.00, so no cashback is earned. This is valid.
- Counter-example: The one where three $0.50 purchases earn $0.01 each, so $0.03 in total, even though $1.50 at 1% would round to $0.02.
- Questions:
  - Is rounding applied to each purchase or to a daily or monthly total?
  - Should tiny amounts accumulate rather than be lost?
  - Which rounding rule is used for reversals?

## Rule 4: Should apply the rate that matches the customer's membership level at the time of purchase

- Example: The one where a standard member and a premium member each spend $100.00, and they earn $1.00 and $2.00.
- Example: The one where a member upgrades on the 15th, so purchases on the 10th earn the old rate and purchases on the 20th earn the new one.
- Counter-example: The one where a customer upgrades the day after a purchase and asks for the higher rate on it. The earlier purchase keeps the old rate.
- Counter-example: The one where a premium membership lapses, and purchases after the lapse earn the standard rate while earlier ones are unchanged.
- Questions:
  - How does a customer become premium, and how do they lose that status?
  - Is there a grace period before the lapse takes effect?
  - Can the rate change for a customer who is in the middle of a promotion?

## Rule 5: Should limit the total cashback a customer can earn in a period

- Example: The one where a customer has earned $49.50 of a $50.00 monthly limit and buys $200.00, so only $0.50 of the $2.00 is credited.
- Example: The one where a customer hit the limit last month and buys on the 1st of the next month, so cashback is earned normally.
- Counter-example: The one where a customer is already at the limit and keeps buying. Purchases go ahead, but no further cashback is earned.
- Counter-example: The one where the limit is reached, and then a refund brings the customer's net cashback below it, so later purchases earn again (if the business agrees).
- Questions:
  - What is the limit, and is it the same for every membership level?
  - Does it apply per customer or per card?
  - Does a refund give room back under the limit?
  - Should the customer be told they are close to the limit?

## Rule 6: Must decide which month a purchase belongs to using the customer's local time

- Example: The one where a customer in Los Angeles buys at 9:00 pm on 31 October, which is already 1 November in UTC, so the purchase counts in October.
- Example: The one where a customer in Auckland buys at 8:00 am on 1 November, which is still 31 October in UTC, so the purchase counts in November.
- Counter-example: The one where a customer moves to another timezone during the month. Purchases already assigned to a month stay where they were.
- Counter-example: The one where a purchase is made on the last day of the month but is only processed on the first of the next. It stays in the month it was made.
- Questions:
  - Is the purchase date the time of the purchase or the date it posts?
  - Where is the customer's timezone taken from, and can the customer change it?
  - What happens when daylight saving time changes?

## Rule 7: Must reverse the cashback when a purchase is refunded, returned or charged back

- Example: The one where a $120.00 purchase earned $1.20 and is fully refunded, so $1.20 is taken back.
- Example: The one where a $120.00 purchase earned $1.20 and $30.00 of it is refunded, so $0.30 is taken back.
- Example: The one where a purchase made in October is refunded in November, so the reversal is recorded in November and October is unchanged.
- Counter-example: The one where a purchase was only partly credited because of the monthly limit, so only the amount actually credited is taken back, not the amount it would have earned.
- Counter-example: The one where a premium member buys, downgrades, and then refunds, so the reversal uses the rate at the time of purchase.
- Counter-example: The one where a customer refunds a purchase that earned nothing, so nothing is reversed.
- Questions:
  - What if the customer has already spent the cashback? Does the balance go negative, or do we hold it back from future cashback?
  - Should the total reversed across several partial refunds ever be more than was earned?
  - How is a dispute or chargeback treated while it is still open?

## Rule 8: Must not earn cashback on transactions that are not purchases

- Example: The one where a customer takes a $200.00 cash advance, so no cashback is earned.
- Example: The one where the customer is charged a card fee or interest, so no cashback is earned.
- Example: The one where the customer pays their card bill, so no cashback is earned.
- Counter-example: The one where a customer buys $40.00 of groceries and takes $20.00 cash back at the till, so cashback is earned on the $40.00 and not on the $20.00.
- Counter-example: The one where a customer buys a gift card, which earns cashback if the business treats it as a purchase.
- Questions:
  - Which transactions count? Gift cards, wallet top-ups, rent, taxes, government payments and money transfers need an answer.
  - Who decides when the merchant's category is unclear or wrong?

## Rule 9: Must never reward the same purchase twice

- Example: The one where the payment network sends the same transaction twice, so cashback is earned once.
- Example: The one where a fuel purchase is first recorded at $50.00 and later corrected to $46.20, so the cashback is adjusted and a second reward is not added.
- Counter-example: The one where a customer buys two identical $5.00 coffees a minute apart, so both earn cashback because they are two purchases.
- Counter-example: The one where a customer buys the same item again after a full refund, so the new purchase earns cashback again.
- Questions:
  - How do we tell a duplicate from a genuine repeat purchase?
  - How late can a correction arrive and still change the cashback?

## Rule 10: Must credit cashback to the right customer

- Example: The one where an authorized user makes a purchase on the primary holder's account, so the cashback goes to the primary holder.
- Example: The one where a customer has two cards, so purchases on both build up the same cashback balance.
- Counter-example: The one where an authorized user's card is closed, so cashback already earned stays with the primary holder.
- Counter-example: The one where a customer closes their account, so any cashback not yet available is handled under the closure terms. This is valid.
- Questions:
  - Who owns the cashback on a joint account?
  - Is any limit shared by the primary holder and authorized users?
  - What happens to cashback when an account is closed?

## Rule 11: Should convert foreign-currency purchases using the amount actually charged

- Example: The one where a customer buys for €50.00 and is charged $54.30, so cashback is earned on $54.30.
- Example: The one where a purchase is made in yen, and cashback is worked out in dollars on the charged amount.
- Counter-example: The one where a foreign transaction fee of $1.63 is added to the charge, so it does not earn cashback.
- Counter-example: The one where a €50.00 purchase is refunded after the exchange rate has moved, so the reversal is based on the cashback originally earned, not on the new dollar value.
- Questions:
  - Do foreign purchases earn at the same rate as domestic ones?
  - Is cashback calculated before or after the foreign transaction fee?

## Rule 12: Should show the customer whether earned cashback is pending or available

- Example: The one where a customer buys today, and the cashback shows as pending until the return window ends.
- Example: The one where a purchase is refunded, and the customer sees both the cashback earned and its reversal.
- Counter-example: The one where a purchase earns $0.00 because of rounding, so no cashback entry is shown.
- Counter-example: The one where a customer is capped for the month, so purchases show no new cashback and the reason is the limit.
- Questions:
  - How long does cashback stay pending?
  - Does cashback expire if it is not used?
  - How and when is the customer notified?
  - Can the customer see how each amount was worked out?

---

## Open questions that affect several rules

1. What are the real rates, tiers and limits? The figures in this map are illustrative.
2. What counts as a qualifying purchase? (Rules 1, 2 and 8)
3. How do refunds interact with the monthly limit and with cashback already spent? (Rules 5 and 7)
4. Is cashback calculated on the purchase date or the date it posts? (Rules 1 and 6)

## Out of scope for this story

- Redeeming or paying out cashback
- Promotions and bonus cashback
- Fraud checks
