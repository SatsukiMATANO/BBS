CREATE VIEW user_post AS
SELECT
	name
	, postmessage.id AS message_id
	, user_id
	, branch_id
	, department_id
	, title
	, category
	, text
	, postmessage.insert_date AS insert_date
FROM
	user, postmessage
WHERE
	user.id = postmessage.user_id;