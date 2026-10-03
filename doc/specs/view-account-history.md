# Feature: View account history

**As a** customer
**I want to** view my cashback transaction history
**So that** I can know where I earn the most cashback

---

## Rule: Must show the customer their own cashback transactions, newest purchase first, for the full account lifetime

Each entry shows merchant, purchase date, purchase amount and cashback amount.

- Example: The one where a customer with several cashback earnings sees each with merchant, purchase date, purchase amount and cashback amount, most recent purchase at the top.
- Example: The one where a long-standing customer can still see entries from their first purchase.
- Counter-example: The one where another customer's transactions (even at the same merchant) never appear in this customer's history.
- Counter-example: The one where an entry confirmed later keeps its position, because ordering follows purchase date, not confirmation date.
- Questions: none open.

## Rule: Must show the status of each cashback entry

| Situation | Status shown | Extra information | Counts as "earned"? |
|---|---|---|---|
| Purchase reported, not yet validated | Pending | Expected confirmation date | No (shown separately) |
| Merchant confirmed, credited to balance | Confirmed | None | Yes |
| Purchase rejected or refunded before confirmation | Cancelled | Reason (e.g. refunded, rejected by merchant) | No |
| Confirmed cashback paid out | Paid out | None | Yes |

- Counter-example: The one where a cancelled entry stays visible in history, with its reason, but is excluded from earnings totals.
- Questions:
  - What are the possible cancellation reasons and their customer-facing wording?
  - What is shown as expected confirmation date when the merchant has not provided one?

## Rule: Must reflect returns after confirmation as separate negative entries, and allow the balance to go negative

- Example: The one where a purchase is partly returned after cashback was confirmed, and a negative adjustment entry appears linked to the original earning.
- Example: The one where a return after payout leaves the balance negative until future cashback offsets it.
- Counter-example: The one where a return before confirmation simply cancels the pending entry and no negative entry is created.
- Questions:
  - Is a negative balance ever collected or written off if the customer stops earning?
  - Should the customer be told the reason for a negative balance?

## Rule: Must show promotional and goodwill bonuses as a distinct entry type

- Example: The one where a sign-up bonus appears in the same list, labelled as a bonus, with no purchase amount.
- Example: The one where a merchant-specific promotion appears as a bonus entry attributed to that merchant.
- Counter-example: The one where a general bonus not tied to a merchant appears in the list but is not counted in any merchant or category ranking.
- Questions:
  - Which category is used for a merchant-specific bonus (the merchant's category)?
  - Do bonuses go through the same statuses (pending, confirmed, cancelled)?

## Rule: Must show payouts as withdrawal entries

- Example: The one where a payout to the customer's bank account appears as a withdrawal entry in the history, so history reconciles with what they received.
- Counter-example: The one where a withdrawal is never counted as earnings in the ranking.
- Counter-example: The one where no running balance is shown per entry.
- Questions:
  - What does a withdrawal entry show (date, amount, destination, payout reference)?
  - What happens to a payout that fails or is returned by the bank?

## Rule: Should show where the customer earns the most, by merchant and by category

Measured by total cashback amount, using confirmed and paid-out cashback only (net of returns).

- Example: The one where the customer sees total cashback per merchant and per category for the selected period, ranked highest to lowest.
- Counter-example: The one where pending and cancelled entries are excluded, so a merchant with only pending cashback does not appear as a top earner.
- Counter-example: The one where a merchant whose earnings were fully offset by returns shows zero and ranks last.
- Counter-example: The one where a merchant-specific bonus is included in that merchant's total, but a general bonus is not.
- Questions:
  - How are ties in total cashback ordered?
  - Is the number of ranked merchants limited (e.g. top 10) or is the whole list shown?

## Rule: Should let the customer limit the history to a time period, defaulting to the last 12 months

Entries fall in a period by purchase date.

| Period selected | Entries included | Ranking uses |
|---|---|---|
| None selected | Last 12 months | Same window |
| Custom range | Purchase date within range, both end dates included | Same range |
| Range with no entries | None | Empty ranking |

- Counter-example: The one where an entry on the last day of the range is included, and one on the next day is not.
- Counter-example: The one where a pending entry bought in the range appears in that period even if it confirms in a later one.
- Questions:
  - Does the default of 12 months mean a rolling window from today or the calendar year?
  - Is "all time" offered as a quick choice, given history is kept for the full lifetime?

## Rule: Should handle a customer with no cashback history

- Example: The one where a new customer sees an empty history with short guidance on how cashback is earned, not an error.
- Counter-example: The one where a customer with only cancelled entries sees them listed, but the ranking is empty.
- Questions: none open.

## Rule: Should load large histories on demand

- Example: The one where a long-standing customer sees the most recent entries first and loads older ones on request, while the ranking still reflects the full selected period, not just what is loaded.
- Questions:
  - How many entries load at a time?

## Rule: Should let the customer download a PDF statement of their history

- Example: The one where the customer downloads a statement for a chosen period, listing the entries of that period.
- Counter-example: The one where a period with no entries produces either no statement or an empty statement (decision needed).
- Questions:
  - Does the statement include the ranking summary as well as the entry list?
  - Is the statement only for a chosen period, or also for all time?
  - Does the statement need to be formally branded or signed for use as proof?

## Rule: Should keep history near real time and say when it was last updated

- Example: The one where a new purchase appears as Pending shortly after the merchant reports it, and the history shows when it was last refreshed.
- Counter-example: The one where a purchase the merchant has not yet reported does not appear at all.
- Questions:
  - What is an acceptable delay between the merchant reporting a purchase and it appearing?
  - What does the customer see if the data source is unavailable?

## Rule: Must show all amounts in the customer's single account currency

- Example: The one where a purchase at a foreign merchant is shown in the account currency.
- Questions:
  - Which exchange rate and date apply to foreign purchases, and is that explained to the customer?

## Rule: Must keep history for the account lifetime plus the legal minimum retention period

- Example: The one where a closed account's history is still retained for the legal period (e.g. 7 years).
- Questions:
  - Can a customer view history after closing their account?
  - Which legal period applies in each market?

## Rule: Must allow support agents read-only access to a customer's history

- Example: The one where a support agent views a customer's history to resolve a query, without being able to change it.
- Counter-example: The one where nobody else (including other customers or joint holders) can view it.
- Questions:
  - Is agent access recorded for audit?
  - Does the agent see exactly what the customer sees, or additional internal detail?
