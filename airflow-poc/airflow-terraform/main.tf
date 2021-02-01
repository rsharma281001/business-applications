resource "aws_instance" "myec2instance" {
	ami = "ami-01720b5f421cf0179"
	instance_type="t2.micro"
	tags = {
		Name = "RishiS"
	}
}
