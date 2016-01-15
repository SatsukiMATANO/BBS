CREATE VIEW user_comment2 AS
SELECT
	name
	, message_id
	, user_id
	, text
	, comment.insert_date AS insert_date
FROM
	user, comment
WHERE
	user.id = comment.user_id;