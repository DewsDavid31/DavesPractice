const std = @import("std");

pub fn main() void {
    std.debug.print("Hello, {s}! Here is a bubble sort!\n", .{"World"});
    var unordered = [_]i32{4,2,6,7,8,9,22,33,1,5,3};
    var middle: i32 = unordered[0];
    std.debug.print("Unsorted array: ",.{});
    for (&unordered) |printable2, printable2_index| {
        std.debug.print("{}", .{printable2});
            if (printable2_index < unordered.len - 1){
                std.debug.print(",",.{});
        }
    }
    for (&unordered) |item1, item_index| {
        for (&unordered) |item2, item2_index| {
            if (item2 > item1) {
                middle = unordered[item2_index];
                unordered[item2_index] = unordered[item_index];
                unordered[item_index] = middle; 
            }
        }
    }
    std.debug.print("\nSorted array: ", .{});
    for (&unordered) |printable, printable_index| {
        std.debug.print("{}", .{printable});
        if (printable_index < unordered.len - 1){
            std.debug.print(",",.{});
        }
    }
    std.debug.print("\n",.{});
}